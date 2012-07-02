package ru.kcode.view;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements Runnable {

    private static final long serialVersionUID = 6690894233205194578L;
    private static JoysticPanel joysticPanel;
    private static RotorView rotorView;

    @Override
    public void run() {
        setTitle("K-Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        rotorView = new RotorView();
        add(rotorView);
        rotorView.setLocation(200, 0);
        
        joysticPanel = new JoysticPanel();
        add(joysticPanel);
        
        pack();
        setVisible(true);
        setSize(410, 410);
    }
    
    public JoysticPanel getJoysticPanel() {
        return joysticPanel;
    }
    
    public RotorView getRotorView() {
        return rotorView;
    }
}
