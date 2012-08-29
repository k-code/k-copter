package ru.kcode.kcontrol.view;

import ru.kcode.kcontrol.service.KJoystick;
import ru.kcode.kcontrol.view.panels.JoystickViewPanel;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class JoystickViewListener implements JoystickListener {

    private JoystickViewPanel joysticPanel;

    public JoystickViewListener(JoystickViewPanel joysticPanel) {
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
