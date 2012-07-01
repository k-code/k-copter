package ru.kcode;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ru.kcode.view.JoysticPanel;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class Controller implements Runnable {

    private static JoysticPanel joysticPanel;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Controller());

        Joystick.createInstance().addJoystickListener(new JoystickListener() {

            public void joystickButtonChanged(Joystick j) {

            }

            public void joystickAxisChanged(Joystick j) {
                if (joysticPanel == null)
                    return;
                joysticPanel.getAxisValueX().setText(
                        Float.valueOf(j.getX()).toString());
                joysticPanel.getAxisValueY().setText(
                        Float.valueOf(j.getR()).toString());
                joysticPanel.getAxisValueZ().setText(
                        Float.valueOf(j.getY()).toString());
                joysticPanel.getAxisValueR().setText(
                        Float.valueOf(j.getZ()).toString());
                joysticPanel.getAxisValueU().setText(
                        Float.valueOf(j.getU()).toString());
                joysticPanel.getAxisValueV().setText(
                        Float.valueOf(j.getV()).toString());
            }
        });
    }

    public void run() {
        JFrame f = new JFrame("Quadro Debug");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.pack();

        joysticPanel = new JoysticPanel();
        f.add(joysticPanel);

        f.setVisible(true);
        f.setSize(320, 320);
    }
}