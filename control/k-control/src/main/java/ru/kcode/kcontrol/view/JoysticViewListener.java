package ru.kcode.kcontrol.view;

import ru.kcode.kcontrol.service.KJoystick;
import ru.kcode.kcontrol.view.panels.JoysticViewPanel;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class JoysticViewListener implements JoystickListener {

    private JoysticViewPanel joysticPanel;

    public JoysticViewListener(JoysticViewPanel joysticPanel) {
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
