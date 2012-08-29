package ru.kcode.kcontrol.service;

import ru.kcode.kcontrol.service.protocol.Protocol;

public interface DriverListener
{
    public void dataReceive(Protocol protocol);
    public void dataTransmit(Protocol protocol);
}
