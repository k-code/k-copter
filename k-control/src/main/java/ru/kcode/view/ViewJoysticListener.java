package ru.kcode.view;

import ru.kcode.service.KJoystick;
import ru.kcode.view.panels.JoysticViewPanel;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class ViewJoysticListener implements JoystickListener {

    private JoysticViewPanel joysticPanel;

    public ViewJoysticListener(JoysticViewPanel joysticPanel) {
        this.joysticPanel = joysticPanel;
    }

    public void joystickAxisChanged(Joystick j) {
        KJoystick kj = new KJoystick(j);
        if (joysticPanel == null)
            return;
        joysticPanel.getAxisValueX().setText(ftos(kj.getX()));
        joysticPanel.getAxisValueY().setText(ftos(kj.getY()));
        joysticPanel.getAxisValueZ().setText(ftos(kj.getZ()));
        joysticPanel.getAxisValueR().setText(ftos(kj.getR()));
    }

    public void joystickButtonChanged(Joystick j) {

    }

    private String ftos(float f) {
        return String.format("%.3f", Float.valueOf(f));
    }
}
