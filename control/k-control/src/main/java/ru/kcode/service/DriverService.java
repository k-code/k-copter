package ru.kcode.service;

import ru.kcode.service.drivers.DeviceDriver;

public class DriverService {
    private static DeviceDriver driver;
    
    private DriverService() {
        // TODO Auto-generated constructor stub
    }
    
    public static DeviceDriver getDriver() {
        return driver;
    }

    public static void setDriver(DeviceDriver d) {
        if (driver != null) {
            driver.stop();
        }
        driver = d;
    }
    
    
}
