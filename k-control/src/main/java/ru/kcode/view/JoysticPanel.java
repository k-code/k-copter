package ru.kcode.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JoysticPanel extends JPanel {
	private static final long serialVersionUID = -3113982496558550127L;
	private GridBagLayout layout;

	private JLabel axisLabelX;
	private JLabel axisLabelY;
	private JLabel axisLabelZ;
	private JLabel axisLabelR;

	private JLabel axisValueX;
	private JLabel axisValueY;
	private JLabel axisValueZ;
	private JLabel axisValueR;
	
	public JoysticPanel() {
	    layout = new GridBagLayout();
		setLayout(layout);
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
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 4;
		c.gridx = 0;
		c.gridy = 0;
        axisLabelY = new JLabel("Y:");
		this.add(axisLabelY, c);
        c.gridx = 0;
        c.gridy = 1;
		axisLabelX = new JLabel("X:");
		this.add(axisLabelX, c);
        c.gridx = 2;
        c.gridy = 0;
		axisLabelZ = new JLabel("Z:");
		this.add(axisLabelZ, c);
        c.gridx = 2;
        c.gridy = 1;
		axisLabelR = new JLabel("R:");
		this.add(axisLabelR, c);
	}
	
	private void initAxisValues() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        axisValueY = new JLabel("0");
        this.add(axisValueY, c);
        c.gridx = 1;
        c.gridy = 1;
		axisValueX = new JLabel("0");
		this.add(axisValueX, c);
        c.gridx = 3;
        c.gridy = 0;
		axisValueZ = new JLabel("0");
		this.add(axisValueZ, c);
        c.gridx = 3;
        c.gridy = 1;
		axisValueR = new JLabel("0");
		this.add(axisValueR, c);
	}
}
