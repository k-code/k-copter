package ru.kcode.kcontrol.view.panels;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import ru.kcode.kcontrol.view.GBLHelper;

public class JoysticViewPanel extends JPanel {
	private static final long serialVersionUID = -3113982496558550127L;

	private JLabel axisLabelX;
	private JLabel axisLabelY;
	private JLabel axisLabelZ;
	private JLabel axisLabelR;

	private JLabel axisValueX;
	private JLabel axisValueY;
	private JLabel axisValueZ;
	private JLabel axisValueR;
	
	public JoysticViewPanel() {
		setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		initAxisLabels();
		initAxisValues();
	}

	public JLabel getAxisValueX() {
		return axisValueX;
	}

	public JLabel getAxisValueY() {
		return axisValueY;
	}

	public JLabel getAxisValueZ() {
		return axisValueZ;
	}

	public JLabel getAxisValueR() {
		return axisValueR;
	}

	private void initAxisLabels() {
	    GBLHelper c = GBLHelper.create().margin(10, 20);
        axisLabelY = new JLabel("Y:");
		this.add(axisLabelY, c.setGrid(0, 0));
		axisLabelX = new JLabel("X:");
		this.add(axisLabelX, c.setGrid(0, 1));
		axisLabelZ = new JLabel("Z:");
		this.add(axisLabelZ, c.setGrid(2, 0));
		axisLabelR = new JLabel("R:");
		this.add(axisLabelR, c.setGrid(2, 1));
	}
	
	private void initAxisValues() {
        GBLHelper c = GBLHelper.create().margin(10, 20, 10, 0);
        axisValueY = new JLabel("0");
        this.add(axisValueY, c.setGrid(1, 0));
		axisValueX = new JLabel("0");
		this.add(axisValueX, c.setGrid(1, 1));
		axisValueZ = new JLabel("0");
		this.add(axisValueZ, c.setGrid(3, 0));
		axisValueR = new JLabel("0");
		this.add(axisValueR, c.setGrid(3, 1));
	}
}
