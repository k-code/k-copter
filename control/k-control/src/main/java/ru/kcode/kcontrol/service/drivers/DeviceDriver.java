package ru.kcode.kcontrol.service.drivers;

import ru.kcode.kcontrol.service.DriverListener;
import ru.kcode.kcontrol.service.protocol.Protocol;

import java.util.HashSet;
import java.util.Set;

public abstract class DeviceDriver {
    private Set<DriverListener> dataTransceiverListeners = new HashSet<DriverListener>();
    
    public abstract void start();
    
    public abstract void stop();
    
    public synchronized void sendData(Protocol p) {
        for (DriverListener listener : dataTransceiverListeners) {
            listener.dataTransmit(p);
        }
    }

    public void receiveData(Protocol p) {
        for (DriverListener listener : dataTransceiverListeners) {
            listener.dataReceive(p);
        }
    }
    
    public abstract String getName();

    public void addDataTransceiverListener(DriverListener listener) {
        dataTransceiverListeners.add(listener);
    }

    public void removeDataTransceiverListener(DriverListener listener) {
        dataTransceiverListeners.remove(listener);
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
