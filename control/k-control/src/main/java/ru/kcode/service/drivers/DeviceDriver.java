package ru.kcode.service.drivers;

import ru.kcode.service.protocol.Protocol;

public abstract class DeviceDriver {
    
    public abstract void start();
    
    public abstract void stop();
    
    public abstract void sendData(Protocol p);
    
    public abstract String getName();
    
    @Override
    public String toString() {
        return getName();
    }
}
