package ru.kcode.kcontrol.view.graphics;

import java.awt.GridBagLayout;

import ru.kcode.kcontrol.view.GBLHelper;
import ru.kcode.kcontrol.view.panels.GraphicPanel;

public class Copter2dPanel extends GraphicPanel {
    private static final long serialVersionUID = 3025339455185444326L;
    
    private RotorView rotorView;

    public Copter2dPanel() {
        super();
        setLayout(new GridBagLayout());
        name = "2D rotor view";
        
        rotorView = new RotorView();
        add(rotorView, GBLHelper.create().fillB());
    }
}
