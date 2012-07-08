package ru.kcode.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ru.kcode.service.DriverService;
import ru.kcode.service.JoystickService;
import ru.kcode.service.drivers.DeviceDriver;
import ru.kcode.service.drivers.USBDebugDriver;

import com.centralnexus.input.Joystick;

public class DevicesPanel extends JPanel {
    private JComboBox joysticksBox;
    private JComboBox driversBox;
    private JButton updateDevicesButton;
    private JButton aplayDriverButton;

    private static final long serialVersionUID = -5935642426362810839L;

    public DevicesPanel() {
        super();
        setLayout(null);
        DevicesPanelListener listener = new DevicesPanelListener();
        joysticksBox = new JComboBox();
        joysticksBox.setBounds(10, 10, 300, 25);
        joysticksBox.setMaximumSize(null);
        add(joysticksBox);

        updateDevicesButton = new JButton("Update");
        updateDevicesButton.setBounds(330, 10, 120, 20);
        updateDevicesButton.addActionListener(listener);
        add(updateDevicesButton);

        aplayDriverButton = new JButton("Aplay");
        aplayDriverButton.setBounds(330, 40, 120, 20);
        aplayDriverButton.addActionListener(listener);
        add(aplayDriverButton);

        driversBox = new JComboBox();
        driversBox.setBounds(10, 40, 300, 25);
        driversBox.addActionListener(listener);
        add(driversBox);
        
        createJoysticksList();
        createDriversList();
    }
    
    public void createJoysticksList() {
        joysticksBox.removeAllItems();
        joysticksBox.addItem("Not found");
        int jCount = Joystick.getNumDevices();
        if (jCount > 0) {
            joysticksBox.removeAllItems();
        }
        for (int i=0; i < jCount; i++) {
            try {
                joysticksBox.addItem(Joystick.createInstance(i));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (joysticksBox.getSelectedItem() instanceof Joystick) {
            JoystickService.setJoystick((Joystick)joysticksBox.getSelectedItem());
        }
        // TODO : else remove listeners and set null
    }
    
    private class DevicesPanelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(updateDevicesButton)) {
                updateDevicesButtonHandler((JButton) e.getSource());
            }
            else if (e.getSource().equals(driversBox)) {
                changeDriverHandler();
            }

            else if (e.getSource().equals(aplayDriverButton)) {
                aplayDriver();
            }
        }
        
        private void updateDevicesButtonHandler(JButton b) {
            createJoysticksList();
        }
        
        private void changeDriverHandler() {
            if (driversBox.getSelectedItem() instanceof DeviceDriver) {
                DriverService.setDriver((USBDebugDriver) driversBox.getSelectedItem());
            }
        }
        
        private void aplayDriver() {
            DriverService.getDriver().start();
        }
    }
    
    private void createDriversList() {
        driversBox.removeAllItems();
        driversBox.addItem("Choice driver");
        driversBox.addItem(new USBDebugDriver());
    }
}
