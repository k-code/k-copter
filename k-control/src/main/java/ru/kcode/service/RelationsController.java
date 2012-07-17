package ru.kcode.service;

import ru.kcode.view.panels.GraphicPanel;

import com.centralnexus.input.Joystick;

public class RelationsController {
    private static Joystick joystick;
    private static GraphicPanel graphicPanel;

    public static void setJoystick(Joystick j) {
        if (joystick != null) {
            // TODO : will make method for delete all listeners
            //joystick.removeJoystickListener(null);
        }
        joystick = j;
        if (graphicPanel != null) {
            graphicPanel.setJoystic(joystick);
        }
    }
    
    public static Joystick getJoystick() {
        return joystick;
    }

    public static GraphicPanel getGraphicPanel() {
        return graphicPanel;
    }

    public static void setGraphicPanel(GraphicPanel gp) {
        graphicPanel = gp;
        if (joystick != null) {
            gp.setJoystic(joystick);
        }
    }
}
