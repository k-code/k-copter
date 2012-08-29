package ru.kcode.kcontrol.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.centralnexus.input.Joystick;

import ru.kcode.kcontrol.service.RelationsController;
import ru.kcode.kcontrol.service.drivers.DeviceDriver;
import ru.kcode.kcontrol.view.graphics.Copter2dPanel;
import ru.kcode.kcontrol.view.graphics.Copter3dPanel;
import ru.kcode.kcontrol.view.panels.JoystickViewPanel;
import ru.kcode.kcontrol.view.panels.settings.ChangeSettingsListener;
import ru.kcode.kcontrol.view.panels.settings.SettingsPanel;

public final class MainWindow extends JFrame implements Runnable {
    private static final long serialVersionUID = 6690894233205194578L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public void run() {
        setDefaultLookAndFeelDecorated(true);
        setTitle("K-Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        SettingsPanel settingsPanel = new SettingsPanel();
        settingsPanel.addListener(new ChangeSettingsListenerImpl());
        mainPanel.add(settingsPanel, GBLHelper.create().setGrid(0, 0).fillH().anchorT().colSpan().weightV(0.1));

        
        JoystickViewPanel joystickViewPanel = new JoystickViewPanel();
        RelationsController.setJoystickView(joystickViewPanel);
        final Copter3dPanel c3p = new Copter3dPanel();
        final Copter2dPanel c2p = new Copter2dPanel();
        RelationsController.setCopter3dView(c3p.getCopter3dView());

        JPanel copterViewPanel = new JPanel(new GridBagLayout());

        copterViewPanel.add(joystickViewPanel, GBLHelper.create().setGrid(0, 0).fillH().anchorT().margin(0, 3));
        copterViewPanel.add(c2p, GBLHelper.create().setGrid(0, 1).fillH().anchorT().weightV(1).margin(20, 3));
        copterViewPanel.add(c3p, GBLHelper.create().setGrid(1, 0).fillB().rowSpan(2).weightV(1).weightH(1));

        JTabbedPane mainTabbedPane = new JTabbedPane();
        mainTabbedPane.add("Copter view", copterViewPanel);

        JPanel graphicsPanel = new JPanel(new GridBagLayout());
        mainTabbedPane.add("Graphics view", graphicsPanel);
        mainTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                System.out.print(1);
                c2p.repaint();
                c3p.repaint();
            }
        });

        mainPanel.add(mainTabbedPane, GBLHelper.create().setGrid(0, 1).fillB().anchorT().colSpan().weightV(0.9));
        
        add(mainPanel, BorderLayout.CENTER);
        
        pack();
    }

    private class ChangeSettingsListenerImpl implements ChangeSettingsListener {

        @Override
        public void changeJoystick(Joystick newJoystick) {
            RelationsController.setJoystick(newJoystick);
        }
        
        @Override
        public void changeDriver(DeviceDriver newDriver) {
            RelationsController.setDriver(newDriver);
        }
    }
}
