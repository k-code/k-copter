package ru.kcode.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kcode.service.drivers.DeviceDriver;
import ru.kcode.view.JoysticViewListener;
import ru.kcode.view.panels.JoysticViewPanel;

import com.centralnexus.input.Joystick;

public class RelationsController {
    private static Logger log = LoggerFactory.getLogger(RelationsController.class);
    private static KJoystick joystick;
    private static JoysticViewPanel joysticView;
    private static JoysticViewListener jvl;
    private static DriverJoystickListener djl;
    private static DeviceDriver driver;

    private RelationsController() {
    }
    
    public static void setJoystick(Joystick j) {
        log.debug("Set joystic: "+j);
        if (joystick != null) {
            joystick.removeAllListeners();
        }
        
        if (j == null) {
            joystick = null;
            return;
        }
        joystick = new KJoystick(j);
        addJoystickViewListener();
        addDriverJoysticListener();
    }
    
    public static KJoystick getJoystick() {
        return joystick;
    }

    public static JoysticViewPanel getJoysticView() {
        return joysticView;
    }

    public static void setJoysticView(JoysticViewPanel jv) {
        log.debug("Set joystick view");
        joysticView = jv;
        addJoystickViewListener();
    }
    
    public static void setDriver(DeviceDriver d) {
        if (driver != null) {
            driver.stop();
        }
        driver = d;
        driver.start();
        addDriverJoysticListener();
    }
    
    private static void addJoystickViewListener() {
        log.debug("Add joystick view listener");
        if (joystick == null || joysticView == null) {
            return;
        }
        if (jvl == null) {
            jvl = new JoysticViewListener(joysticView);
        }
        joystick.addListener(jvl);
    }
    
    private static void addDriverJoysticListener() {
        if (joystick == null || driver == null) {
            return;
        }
        if (djl == null) {
            djl = new DriverJoystickListener(driver);
        }
        joystick.addListener(djl);
    }
}
