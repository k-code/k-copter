package ru.kcode.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.centralnexus.input.Joystick;

import ru.kcode.service.RelationsController;
import ru.kcode.view.graphics.Copter2dPanel;
import ru.kcode.view.graphics.Copter3dPanel;
import ru.kcode.view.panels.GraphicPanel;
import ru.kcode.view.panels.JoysticViewPanel;
import ru.kcode.view.panels.settings.ChangeSettingsListener;
import ru.kcode.view.panels.settings.SettingsPanel;

public final class MainWindow extends JFrame implements Runnable {
    private static final long serialVersionUID = 6690894233205194578L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    private JPanel mainPanel;
    private SettingsPanel settingsPanel;
    private JoysticViewPanel joysticViewPanel;

    @Override
    public void run() {
        setDefaultLookAndFeelDecorated(true);
        setTitle("K-Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setMinimumSize(size);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel, BorderLayout.CENTER);
        
        settingsPanel = new SettingsPanel();;
        settingsPanel.addListener(new ChangeSettingsListenerImpl());
        mainPanel.add(settingsPanel, GBLHelper.create().setGrid(0, 0).fillH().anchorT().colSpan().weightV(0.1));

        joysticViewPanel = new JoysticViewPanel();
        RelationsController.setJoysticView(joysticViewPanel);
        mainPanel.add(joysticViewPanel, GBLHelper.create().setGrid(0, 1).fillH().anchorT().margin(0, 3));
        mainPanel.add(new Copter2dPanel(), GBLHelper.create().setGrid(0, 2).fillH().anchorT().weightV(1).margin(20, 3));
        mainPanel.add(new Copter3dPanel(), GBLHelper.create().setGrid(1, 1).fillB().rowSpan(2).weightV(1).weightH(1));
        
        pack();
    }

    private class ChangeSettingsListenerImpl implements ChangeSettingsListener {

        @Override
        public void changeJoystic(Joystick newJoystick) {
            RelationsController.setJoystick(newJoystick);
        }
    }
}
