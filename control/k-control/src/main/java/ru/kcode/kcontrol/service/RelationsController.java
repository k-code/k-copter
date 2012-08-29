package ru.kcode.kcontrol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kcode.kcontrol.service.drivers.DeviceDriver;
import ru.kcode.kcontrol.view.JoystickViewListener;
import ru.kcode.kcontrol.view.graphics.Copter3dView;
import ru.kcode.kcontrol.view.panels.JoystickViewPanel;

import com.centralnexus.input.Joystick;

public class RelationsController {
    private static Logger log = LoggerFactory.getLogger(RelationsController.class);
    private static KJoystick joystick;
    private static JoystickViewPanel joystickView;
    private static JoystickViewListener jvl;
    private static DriverJoystickListener djl;
    private static DeviceDriver driver;
    private static Copter3dView copter3dView;

    private RelationsController() {
    }
    
    public static void setJoystick(Joystick j) {
        log.debug("Set joystick: "+j);
        if (joystick != null) {
            joystick.removeAllListeners();
        }
        
        if (j == null) {
            joystick = null;
            return;
        }
        joystick = new KJoystick(j);
        addJoystickViewListener();
        addDriverJoystickListener();
    }
    
    public static KJoystick getJoystick() {
        return joystick;
    }

    public static JoystickViewPanel getJoystickView() {
        return joystickView;
    }

    public static void setJoystickView(JoystickViewPanel jv) {
        log.debug("Set joystick view");
        joystickView = jv;
        addJoystickViewListener();
    }
    
    public static void setDriver(DeviceDriver d) {
        if (driver != null) {
            driver.stop();
        }
        driver = d;
        driver.start();
        addDriverJoystickListener();
    }
    
    private static void addJoystickViewListener() {
        log.debug("Add joystick view listener");
        if (joystick == null || joystickView == null) {
            return;
        }
        if (jvl == null) {
            jvl = new JoystickViewListener(joystickView);
        }
        joystick.addListener(jvl);
    }
    
    private static void addDriverJoystickListener() {
        if (joystick == null || driver == null) {
            return;
        }
        if (djl == null) {
            djl = new DriverJoystickListener(driver);
        }
        joystick.addListener(djl);
    }

    public static Copter3dView getCopter3dView() {
        return copter3dView;
    }

    public static void setCopter3dView(Copter3dView copter3dView) {
        RelationsController.copter3dView = copter3dView;
    }
}
