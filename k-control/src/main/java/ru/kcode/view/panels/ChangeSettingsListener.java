package ru.kcode.view.panels;

import com.centralnexus.input.Joystick;


public interface ChangeSettingsListener {
    public void changeGraphicPanel(GraphicPanel newPanel);
    
    public void changeJoystic(Joystick newJoystick);
}
