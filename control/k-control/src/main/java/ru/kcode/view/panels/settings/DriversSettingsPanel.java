package ru.kcode.view.panels.settings;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ru.kcode.service.RelationsController;
import ru.kcode.service.drivers.DeviceDriver;
import ru.kcode.service.drivers.USBDebugDriver;
import ru.kcode.view.GBLHelper;

public class DriversSettingsPanel extends JPanel {
    private static final long serialVersionUID = -8581809031436070964L;

    private JComboBox driversBox;
    private JButton applayDriverButton;

    public DriversSettingsPanel() {
        super();
        setBorder(BorderFactory.createTitledBorder("Drivers settings"));
        setLayout(new GridBagLayout());
        init();
    }
    
    private void init() {
        DriversPanelListener listener = new DriversPanelListener();
        driversBox = new JComboBox();
        driversBox.addActionListener(listener);
        add(driversBox, GBLHelper.create().weightH(0.8).fillH().margin(2, 3).setGrid(0, 1));

        applayDriverButton = new JButton("Applay");
        applayDriverButton.addActionListener(listener);
        add(applayDriverButton, GBLHelper.create().weightH(0.2).fillH().margin(2, 3).setGrid(1, 1));

        createDriversList();
    }
    private void createDriversList() {
        driversBox.removeAllItems();
        driversBox.addItem("Choice driver");
        driversBox.addItem(new USBDebugDriver());
    }
    
    private void aplayDriver() {
        if (driversBox.getSelectedItem() instanceof DeviceDriver) {
            RelationsController.setDriver((DeviceDriver) driversBox.getSelectedItem());
        }
    }

    private class DriversPanelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source.equals(applayDriverButton)) {
                aplayDriver();
            }
        }
        
    }
}
