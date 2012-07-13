package ru.kcode.view.panels;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import ru.kcode.service.DriverService;
import ru.kcode.service.JoystickService;
import ru.kcode.service.drivers.DeviceDriver;
import ru.kcode.service.drivers.USBDebugDriver;
import ru.kcode.view.GBLHelper;
import ru.kcode.view.graphics.Copter2dPanel;
import ru.kcode.view.graphics.Copter3dPanel;

import com.centralnexus.input.Joystick;

public class SettingsPanel extends JPanel {
    private Set<ChangeSettingsListener> changeSettingsListeners;
    private JComboBox joysticksBox;
    private JButton updateDevicesButton;
    private JComboBox driversBox;
    private JButton applayDriverButton;
    private JComboBox graphicsModeBox;
    private JButton applayViewModeButton;

    private static final long serialVersionUID = -5935642426362810839L;

    public SettingsPanel() {
        super();
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        changeSettingsListeners = new HashSet<ChangeSettingsListener>();
        DevicesPanelListener listener = new DevicesPanelListener();
        
        GBLHelper comboBoxesConst = GBLHelper.create().weightH(0.7).fillH().margin(2, 3);
        GBLHelper buttonsConst = GBLHelper.create().weightH(0.3).fillH().margin(2, 3);
        
        joysticksBox = new JComboBox();
        joysticksBox.setMaximumSize(null);
        add(joysticksBox, comboBoxesConst.setGrid(0, 0));

        updateDevicesButton = new JButton("Update");
        updateDevicesButton.addActionListener(listener);
        add(updateDevicesButton, buttonsConst.setGrid(1, 0));

        driversBox = new JComboBox();
        driversBox.addActionListener(listener);
        add(driversBox, comboBoxesConst.setGrid(0, 1));

        applayDriverButton = new JButton("Applay");
        applayDriverButton.addActionListener(listener);
        add(applayDriverButton, buttonsConst.setGrid(1, 1));
        
        graphicsModeBox = new JComboBox();
        graphicsModeBox.addActionListener(listener);
        graphicsModeBox.addItem(new Copter3dPanel());
        graphicsModeBox.addItem(new Copter2dPanel());
        add(graphicsModeBox, comboBoxesConst.setGrid(0, 2));
        
        applayViewModeButton = new JButton("Applay");
        applayViewModeButton.addActionListener(listener);
        add(applayViewModeButton, buttonsConst.setGrid(1, 2));
        
        createJoysticksList();
        createDriversList();
    }

    public void addListener(ChangeSettingsListener listener) {
        changeSettingsListeners.add(listener);
    }
    
    public void removeListener(ChangeSettingsListener listener) {
        changeSettingsListeners.remove(listener);
    }
    
    private void createJoysticksList() {
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
    
    private void createDriversList() {
        driversBox.removeAllItems();
        driversBox.addItem("Choice driver");
        driversBox.addItem(new USBDebugDriver());
    }
    
    private class DevicesPanelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(updateDevicesButton)) {
                updateDevicesButtonHandler((JButton) e.getSource());
            }
            else if (source.equals(driversBox)) {
                changeDriverHandler();
            }

            else if (source.equals(applayDriverButton)) {
                aplayDriver();
            }
            else if(source.equals(applayViewModeButton)) {
                fireChengeGraphicsSettings();
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
    
    private void fireChengeGraphicsSettings() {
        for (ChangeSettingsListener l: changeSettingsListeners) {
            l.changeGraphicPanel((GraphicPanel)graphicsModeBox.getSelectedItem());
        }
    }
}
