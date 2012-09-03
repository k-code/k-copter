package ru.kcode.kcontrol.test.service;

import gnu.io.NRSerialPort;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * User: kcode
 * Date: 03.09.12
 * Time: 23:24
 */
public class NativeTest {

    @Test
    public void testComPort() {
        try {
            NRSerialPort.getAvailableSerialPorts();
        }
        catch (Exception e) {
            fail("Fail init native serial ports");
        }
    }
}
