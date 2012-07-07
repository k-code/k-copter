package ru.kcode.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.kcode.service.JoystickService;
import ru.kcode.view.copter.Copetr3dPanel;
import ru.kcode.view.copter.RotorView;

import com.centralnexus.input.Joystick;

public class MainWindow extends JFrame implements Runnable {
    private static final long serialVersionUID = 6690894233205194578L;
    private static DevicesPanel devicesPanel;
    private static JoysticPanel joysticPanel;
    private static RotorView rotorView;
    private static Copetr3dPanel copetr3dPanel;

    @Override
    public void run() {
        setDefaultLookAndFeelDecorated(true);
        setTitle("K-Control");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        
        devicesPanel = new DevicesPanel();
        devicesPanel.setBounds(2, 2, 480, 80);
        mainPanel.add(devicesPanel);

        joysticPanel = new JoysticPanel();
        joysticPanel.setBounds(2, 80, 200, 200);
        mainPanel.add(joysticPanel);

        rotorView = new RotorView();
        rotorView.setBounds(220, 80, 200, 200);
        mainPanel.add(rotorView);

        copetr3dPanel = new Copetr3dPanel();
        copetr3dPanel.setBounds(2, 280, 400, 400);
        mainPanel.add(copetr3dPanel);
        
        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(500, 900));
        
        Joystick j = JoystickService.getJoystick();
        if (j != null) {
            j.addJoystickListener(new ViewJoysticListener(joysticPanel));
            j.addJoystickListener(new ViewRotorJoysticListener(rotorView));
        }
    }
    
    public JoysticPanel getJoysticPanel() {
        return joysticPanel;
    }
    
    public RotorView getRotorView() {
        return rotorView;
    }
}
