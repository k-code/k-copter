package ru.kcode.view;

import ru.kcode.service.KJoystick;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class ViewJoysticListener implements JoystickListener {

    private JoysticPanel joysticPanel;

    public ViewJoysticListener(JoysticPanel joysticPanel) {
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
        joysticPanel.getAxisValueU().setText(ftos(kj.getU()));
        joysticPanel.getAxisValueV().setText(ftos(kj.getV()));
    }

    public void joystickButtonChanged(Joystick j) {

    }

    private String ftos(float f) {
        return String.format("%.3f", Float.valueOf(f));
    }
}
