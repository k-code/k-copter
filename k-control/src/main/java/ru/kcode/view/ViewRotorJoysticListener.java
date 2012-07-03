package ru.kcode.view;

import ru.kcode.service.KJoystick;
import ru.kcode.service.MotorComputing;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class ViewRotorJoysticListener implements JoystickListener {
    private RotorView rotorView;

    public ViewRotorJoysticListener(RotorView rotorView) {
        this.rotorView = rotorView;
    }

    @Override
    public void joystickAxisChanged(Joystick j) {
        MotorComputing mc = MotorComputing.getInstance(new KJoystick(j));
        rotorView.update(mc.getMotor0(), mc.getMotor1(), mc.getMotor2(), mc.getMotor3());
    }

    @Override
    public void joystickButtonChanged(Joystick j) {
    }

}
