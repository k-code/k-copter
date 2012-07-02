package ru.kcode.view;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class ViewRotorJoysticListener implements JoystickListener {
    private RotorView rotorView;

    public ViewRotorJoysticListener(RotorView rotorView) {
        this.rotorView = rotorView;
    }

    @Override
    public void joystickAxisChanged(Joystick j) {
        int m0 = Math.round(j.getX()*1000);
        int m1 = Math.round(j.getY()*1000);
        int m2 = Math.round(j.getZ()*1000);
        int m3 = Math.round(j.getR()*1000);
        rotorView.repaint(m0, m1, m2, m3);
    }

    @Override
    public void joystickButtonChanged(Joystick j) {
    }

}
