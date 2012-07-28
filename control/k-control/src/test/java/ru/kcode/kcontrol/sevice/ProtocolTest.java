package ru.kcode.kcontrol.sevice;

import org.junit.Ignore;
import org.junit.Test;

import ru.kcode.service.Protocol;
import ru.kcode.service.drivers.USBDebugDriver;

public class ProtocolTest {

    @Ignore
    @Test
    public void testCrateProtocol() {
        Protocol p = new Protocol();
        p.addParam(Protocol.MOTOR_1, 12345);
        System.out.println(p.toString());

        p = new Protocol();
        p.addParam(Protocol.MOTOR_2, 54321);
        p.addParam(Protocol.MESSAGE, "1 2 a b");
        System.out.println(p.toString());
        System.out.println(p.getMess().length);
    }

    @Test
    public void testDriver() throws Exception {
        USBDebugDriver dr = new USBDebugDriver();
        Protocol p = new Protocol();
        dr.start();
        p.addParam(Protocol.MOTOR_1, 1000);
        p.addParam(Protocol.MOTOR_2, 1000);
        p.addParam(Protocol.MOTOR_3, 1000);
        p.addParam(Protocol.MOTOR_4, 1000);
        dr.sendData(p);
        dr.stop();
    }
    
    @Test
    public void testDriver2() throws Exception {
        USBDebugDriver dr = new USBDebugDriver();
        Protocol p = new Protocol();
        dr.start();
        p.addParam(Protocol.MOTOR_1, 1000);
        p.addParam(Protocol.MOTOR_2, 1000);
        p.addParam(Protocol.MOTOR_3, 1000);
        p.addParam(Protocol.MOTOR_4, 1000);
        dr.sendData(p);
        dr.stop();
    }
}
