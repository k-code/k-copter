package ru.kcode;

import java.io.IOException;

import javax.swing.SwingUtilities;

import ru.kcode.view.MainWindow;
import ru.kcode.view.ViewJoysticListener;
import ru.kcode.view.ViewRotorJoysticListener;

import com.centralnexus.input.Joystick;

public class Controller {

    public static void main(String[] args) throws Exception {
        Joystick joystick;
        MainWindow mainWindow;

        mainWindow = new MainWindow();
        SwingUtilities.invokeAndWait(mainWindow);

        try {
            joystick = Joystick.createInstance();
            if (joystick != null) {
                joystick.addJoystickListener(new ViewJoysticListener(mainWindow
                        .getJoysticPanel()));
                joystick.addJoystickListener(new ViewRotorJoysticListener(mainWindow
                        .getRotorView()));
            }
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}