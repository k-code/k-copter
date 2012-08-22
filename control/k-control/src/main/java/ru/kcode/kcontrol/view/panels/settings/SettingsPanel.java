package ru.kcode.kcontrol.view.panels.settings;

import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import ru.kcode.kcontrol.view.GBLHelper;

public class SettingsPanel extends JPanel {
    private Set<ChangeSettingsListener> changeSettingsListeners;

    private static final long serialVersionUID = -5935642426362810839L;

    public SettingsPanel() {
        super();
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        
        changeSettingsListeners = new HashSet<ChangeSettingsListener>();
        
        GBLHelper c = GBLHelper.create().fillH().margin(2, 3);
        add(new JoysticSettingsPanel(), c.weightH(0.7).setGrid(0, 0));
        add(new DriversSettingsPanel(), c.weightH(0.3).setGrid(1, 0));
    }

    public void addListener(ChangeSettingsListener listener) {
        changeSettingsListeners.add(listener);
    }
    
    public void removeListener(ChangeSettingsListener listener) {
        changeSettingsListeners.remove(listener);
    }
}
