package ru.kcode.view;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class ViewJoysticListener implements JoystickListener {

    private JoysticPanel joysticPanel;

    public ViewJoysticListener(JoysticPanel joysticPanel) {
        this.joysticPanel = joysticPanel;
    }

    public void joystickAxisChanged(Joystick j) {
        if (joysticPanel == null)
            return;
        joysticPanel.getAxisValueX().setText(ftos(j.getX()));
        joysticPanel.getAxisValueY().setText(ftos(j.getR()));
        joysticPanel.getAxisValueZ().setText(ftos(j.getY()));
        joysticPanel.getAxisValueR().setText(ftos(j.getZ()));
        joysticPanel.getAxisValueU().setText(ftos(j.getU()));
        joysticPanel.getAxisValueV().setText(ftos(j.getV()));
    }

    public void joystickButtonChanged(Joystick j) {

    }

    private String ftos(float f) {
        return String.format("%.3f", Float.valueOf(f));
    }
}
