package ru.kcode.service;

import com.centralnexus.input.Joystick;

public class KJoystick {
    private Joystick joystick;
    
    public KJoystick(Joystick j) {
        joystick = j;
    }
    
    public float getX() {
        return joystick.getX();
    }
    
    public float getY() {
        return invert(joystick.getY());
    }
    
    public float getZ() {
        return invert(joystick.getR());
    }
    
    public float getR() {
        return joystick.getZ();
    }
    
    public float getU() {
        return joystick.getU();
    }
    
    public float getV() {
        return joystick.getV();
    }

    private float invert(float axis) {
        return axis == 0 ? 0 : -axis;
    }
}
