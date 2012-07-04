package ru.kcode.service;

import com.centralnexus.input.Joystick;

public class JoystickService {
    private static Joystick joystick;

    public static void setJoystick(Joystick j) {
        joystick = j;
    }
    
    public static Joystick getJoystick() {
        return joystick;
    }
}
