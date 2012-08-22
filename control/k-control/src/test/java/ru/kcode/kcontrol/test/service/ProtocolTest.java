package ru.kcode.kcontrol.test.service;

import org.junit.Ignore;
import org.junit.Test;

import ru.kcode.kcontrol.service.drivers.USBDebugDriver;
import ru.kcode.kcontrol.service.protocol.Frame;
import ru.kcode.kcontrol.service.protocol.Protocol;

public class ProtocolTest {

    @Ignore
    @Test
    public void testCrateProtocol() {
        Protocol p = new Protocol();
        p.addParam(Frame.MOTOR_1, 12345);
        System.out.println(p.toString());

        p = new Protocol();
        p.addParam(Frame.MOTOR_2, 54321);
        System.out.println(p.toString());
        System.out.println(p.getMess().length);
    }

    @Test
    public void testDriver() throws Exception {
        USBDebugDriver dr = new USBDebugDriver();
        Protocol p = new Protocol();
        dr.start();
        p.addParam(Frame.MOTOR_1, 1000);
        p.addParam(Frame.MOTOR_2, 1000);
        p.addParam(Frame.MOTOR_3, 1000);
        p.addParam(Frame.MOTOR_4, 1000);
        dr.sendData(p);
        dr.stop();
    }
    
    @Test
    public void testDriver2() throws Exception {
        USBDebugDriver dr = new USBDebugDriver();
        Protocol p = new Protocol();
        dr.start();
        p.addParam(Frame.MOTOR_1, 1000);
        p.addParam(Frame.MOTOR_2, 1000);
        p.addParam(Frame.MOTOR_3, 1000);
        p.addParam(Frame.MOTOR_4, 1000);
        dr.sendData(p);
        dr.stop();
    }
}
