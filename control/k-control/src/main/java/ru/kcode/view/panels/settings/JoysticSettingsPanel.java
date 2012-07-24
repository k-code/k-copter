package ru.kcode.view.panels.settings;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.centralnexus.input.Joystick;

import ru.kcode.service.RelationsController;
import ru.kcode.view.GBLHelper;

public class JoysticSettingsPanel extends JPanel {
    private static final long serialVersionUID = -1854448935344200361L;

    private JComboBox joysticksBox;
    private JButton updateDevicesButton;

    public JoysticSettingsPanel() {
        super();
        setBorder(BorderFactory.createTitledBorder("Joystick settings"));
        setLayout(new GridBagLayout());
        init();
    }

    private void init() {
        JoysticPanelListener listener = new JoysticPanelListener();
        joysticksBox = new JComboBox();
        joysticksBox.addActionListener(listener);
        add(joysticksBox, GBLHelper.create().weightH(1).fillH().margin(2, 3).setGrid(0, 0));

        updateDevicesButton = new JButton("Update");
        updateDevicesButton.addActionListener(listener);
        add(updateDevicesButton, GBLHelper.create().weightH(0.1).fillH().margin(2, 3).setGrid(1, 0));

        createJoysticksList();
    }

    private void createJoysticksList() {
        joysticksBox.removeAllItems();
        joysticksBox.addItem("Choise joystick");
        int jCount = Joystick.getNumDevices();
        for (int i = 0; i < jCount; i++) {
            try {
                joysticksBox.addItem(Joystick.createInstance(i));
                joysticksBox.setMinimumSize(new Dimension(100, 20));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (joysticksBox.getSelectedItem() instanceof Joystick) {
            RelationsController.setJoystick((Joystick) joysticksBox
                    .getSelectedItem());
            fireChengeJoysticSettings();
        }
        // TODO : else remove listeners and set null
    }

    private void updateDevicesButtonHandler(JButton b) {
        createJoysticksList();
    }
    
    private void fireChengeJoysticSettings() {
        if (joysticksBox.getSelectedItem() instanceof Joystick) {
            RelationsController.setJoystick((Joystick)joysticksBox.getSelectedItem());
        }
    }

    private class JoysticPanelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(updateDevicesButton)) {
                updateDevicesButtonHandler((JButton) e.getSource());
            } else if (source.equals(joysticksBox)) {
                fireChengeJoysticSettings();
            }

        }
    }
}
