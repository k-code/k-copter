package ru.kcode.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JoysticPanel extends JPanel {
	private static final long serialVersionUID = -3113982496558550127L;

	private JLabel axisLabelX;
	private JLabel axisLabelY;
	private JLabel axisLabelZ;
	private JLabel axisLabelR;
	private JLabel axisLabelU;
	private JLabel axisLabelV;

	private JLabel axisValueX;
	private JLabel axisValueY;
	private JLabel axisValueZ;
	private JLabel axisValueR;
	private JLabel axisValueU;
	private JLabel axisValueV;
	
	public JoysticPanel() {
		this.setLayout(null);
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

	public JLabel getAxisValueU() {
		return axisValueU;
	}

	public JLabel getAxisValueV() {
		return axisValueV;
	}

	private void initAxisLabels() {
		axisLabelY = new JLabel("Y:");
		axisLabelY.setBounds(10, 10, 15, 10);
		this.add(axisLabelY);
		axisLabelX = new JLabel("X:");
		axisLabelX.setBounds(10, 25, 15, 10);
		this.add(axisLabelX);
		axisLabelZ = new JLabel("Z:");
		axisLabelZ.setBounds(110, 10, 15, 10);
		this.add(axisLabelZ);
		axisLabelR = new JLabel("R:");
		axisLabelR.setBounds(110, 25, 15, 10);
		this.add(axisLabelR);
		axisLabelU = new JLabel("U:");
		axisLabelU.setBounds(10, 60, 15, 10);
		this.add(axisLabelU);
		axisLabelV = new JLabel("V:");
		axisLabelV.setBounds(110, 60, 15, 10);
		this.add(axisLabelV);
	}
	
	private void initAxisValues() {
        axisValueY = new JLabel("y");
        axisValueY.setBounds(25, 10, 50, 10);
        this.add(axisValueY);
		axisValueX = new JLabel("x");
		axisValueX.setBounds(25, 25, 50, 10);
		this.add(axisValueX);
		axisValueZ = new JLabel("z");
		axisValueZ.setBounds(135, 10, 50, 10);
		this.add(axisValueZ);
		axisValueR = new JLabel("r");
		axisValueR.setBounds(135, 25, 50, 10);
		this.add(axisValueR);
		axisValueU = new JLabel("u");
		axisValueU.setBounds(25, 60, 50, 10);
		this.add(axisValueU);
		axisValueV = new JLabel("v");
		axisValueV.setBounds(135, 60, 50, 10);
		this.add(axisValueV);
	}
}
