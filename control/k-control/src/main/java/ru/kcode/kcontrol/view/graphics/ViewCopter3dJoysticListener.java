package ru.kcode.kcontrol.view.graphics;

import ru.kcode.kcontrol.service.KJoystick;
import ru.kcode.kcontrol.service.MotorComputing;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class ViewCopter3dJoysticListener implements JoystickListener {
    private Copter3dView copter3dView;

    public ViewCopter3dJoysticListener(Copter3dView copter3dView) {
        this.copter3dView = copter3dView;
    }

    @Override
    public void joystickAxisChanged(Joystick j) {
        MotorComputing mc = MotorComputing.getInstance(new KJoystick(j));
        copter3dView.setXAngle(mc.getXAngel());
        copter3dView.setYAngle(mc.getYAngel());
        copter3dView.setZAngle(mc.getZAngel());
    }

    @Override
    public void joystickButtonChanged(Joystick j) {
    }

}
