package ru.kcode.view.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.centralnexus.input.Joystick;

public abstract class GraphicPanel extends JPanel {
    private static final long serialVersionUID = -3668909495813220494L;
    protected String name = "GraphicPanel";
    protected Joystick joystick;

    public GraphicPanel() {
        super();
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    public void setJoystic(Joystick j) {
        joystick = j;
    }
}
