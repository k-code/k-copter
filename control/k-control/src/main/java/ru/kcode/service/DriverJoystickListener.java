package ru.kcode.service;

import ru.kcode.service.drivers.DeviceDriver;

import com.centralnexus.input.Joystick;
import com.centralnexus.input.JoystickListener;

public class DriverJoystickListener implements JoystickListener {
    private DeviceDriver driver;

    public DriverJoystickListener(DeviceDriver d) {
        driver = d;
    }
    
    @Override
    public void joystickAxisChanged(Joystick joystick) {
        if (driver == null) {
            return;
        }
        KJoystick j = new KJoystick(joystick);
        Protocol p = new Protocol();
        MotorComputing mc = MotorComputing.getInstance(j);

        p.addParam(Protocol.MOTOR_1, mc.getMotor0());
        p.addParam(Protocol.MOTOR_2, mc.getMotor1());
        p.addParam(Protocol.MOTOR_3, mc.getMotor2());
        p.addParam(Protocol.MOTOR_4, mc.getMotor3());
        
        driver.sendData(p);
    }

    @Override
    public void joystickButtonChanged(Joystick j) {
        // TODO Auto-generated method stub
        
    }

    public DeviceDriver getDriver() {
        return driver;
    }

    public void setDriver(DeviceDriver driver) {
        this.driver = driver;
    }

}
