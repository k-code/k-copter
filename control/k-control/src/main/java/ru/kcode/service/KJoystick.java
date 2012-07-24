package ru.kcode.service;

import java.util.ArrayList;
import java.util.List;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class KJoystick {
    private Joystick joystick;
    private List<JoystickListener> listeners = new ArrayList<JoystickListener>();
    
    public KJoystick(Joystick j) {
        joystick = j;
    }
    
    public void addListener(JoystickListener listener) {
        joystick.addJoystickListener(listener);
        listeners.add(listener);
    }
    
    public void remmoveListener(JoystickListener listener) {
        joystick.removeJoystickListener(listener);
        listeners.remove(listener);
    }
    
    public void removeAllListeners() {
        List<JoystickListener> tmpListeners = new ArrayList<JoystickListener>(listeners);
        for (JoystickListener l : tmpListeners) {
            remmoveListener(l);
        }
    }
    
    public Joystick getJoystick() {
        return joystick;
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
