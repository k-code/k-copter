package ru.kcode.view.panels.settings;

import ru.kcode.service.drivers.DeviceDriver;

import com.centralnexus.input.Joystick;

public interface ChangeSettingsListener {
    public void changeJoystic(Joystick newJoystick);
    public void changeDriver(DeviceDriver newDriver);
}
