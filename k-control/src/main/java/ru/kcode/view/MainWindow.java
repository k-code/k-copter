package ru.kcode.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.kcode.view.panels.ChangeSettingsListener;
import ru.kcode.view.panels.GraphicPanel;
import ru.kcode.view.panels.SettingsPanel;

public final class MainWindow extends JFrame implements Runnable {
    private static final long serialVersionUID = 6690894233205194578L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 600;
    
    private JPanel mainPanel;
    private SettingsPanel settingsPanel;
    private GraphicPanel graphicPanel;

    @Override
    public void run() {
        setDefaultLookAndFeelDecorated(true);
        setTitle("K-Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel, BorderLayout.CENTER);
        
        settingsPanel = new SettingsPanel();;
        settingsPanel.addListener(new ChangeSettingsListenerImpl());
        mainPanel.add(settingsPanel, GBLHelper.create().setGrid(0, 0).fillH().anchorT());
        
        pack();
    }

    private class ChangeSettingsListenerImpl implements ChangeSettingsListener {
        
        @Override
        public void changeGraphicPanel(GraphicPanel newPanel) {
            if (graphicPanel != null) {
                mainPanel.remove(graphicPanel);
            }
            if (newPanel != null) {
                graphicPanel = newPanel;

                mainPanel.add(graphicPanel, GBLHelper.create().setGrid(0, 1).fillB().weightV(1));
                pack();
            }
        }
    }
}
