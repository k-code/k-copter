package ru.kcode.service.drivers;

import ru.kcode.service.MotorComputing;

public abstract class DeviceDriver {
    public abstract void start();
    public abstract void stop();
    public abstract void sendData(MotorComputing mc);
    public abstract String getName();
    
    @Override
    public String toString() {
        return getName();
    }
}
