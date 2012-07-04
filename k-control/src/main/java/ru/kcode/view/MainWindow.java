package ru.kcode.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.kcode.service.JoystickService;

import com.centralnexus.input.Joystick;

public class MainWindow extends JFrame implements Runnable {
    private static final long serialVersionUID = 6690894233205194578L;
    private static DevicesPanel devicesPanel;
    private static JoysticPanel joysticPanel;
    private static RotorView rotorView;

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
        
        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(500, 400));
        
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
