package ru.kcode.kcontrol.view.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class RotorView extends Canvas {
    private static final int CANVAS_WIDTH = 200;
    private static final int CANVAS_HEIGHT = 200;
    private static final int CANVAS_CENTER_X = CANVAS_WIDTH / 2;
    private static final int CANVAS_CENTER_Y = CANVAS_HEIGHT / 2;
    private static final int QC_SHAFT_LENGTH = 50;
    private static final int QC_ROTOR_SIZE = 60;
    private static final int QC_SHAFT_DELTA = (int) Math.round(Math.sqrt((QC_SHAFT_LENGTH * QC_SHAFT_LENGTH)/2));
    private static final int QC_ROTOR_DELTA = (int) Math.round(Math.sqrt(
            ( (QC_SHAFT_LENGTH + QC_ROTOR_SIZE/2) * (QC_SHAFT_LENGTH + QC_ROTOR_SIZE/2) ) /2 ));
    private static final int QC_ROTOR_START_ANGLE = 0;
    private int motorValue0 = 0;
    private int motorValue1 = 0;
    private int motorValue2 = 0;
    private int motorValue3 = 0;

    private static final long serialVersionUID = -6577075623577413434L;

    public RotorView() {
        super();
        this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drowShafts(g);
        drowRotor(g);
        drawRotorState(g);
    }

    private void drowShafts(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y, CANVAS_CENTER_X
                - QC_SHAFT_DELTA, CANVAS_CENTER_Y - QC_SHAFT_DELTA);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y, CANVAS_CENTER_X
                + QC_SHAFT_DELTA, CANVAS_CENTER_Y - QC_SHAFT_DELTA);

        g.setColor(Color.BLACK);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y, CANVAS_CENTER_X
                - QC_SHAFT_DELTA, CANVAS_CENTER_Y + QC_SHAFT_DELTA);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y, CANVAS_CENTER_X
                + QC_SHAFT_DELTA, CANVAS_CENTER_Y + QC_SHAFT_DELTA);

    }

    private void drowRotor(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(CANVAS_CENTER_X - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                CANVAS_CENTER_Y - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                QC_ROTOR_SIZE, QC_ROTOR_SIZE);
        g.drawOval(CANVAS_CENTER_X + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                CANVAS_CENTER_Y  - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                QC_ROTOR_SIZE, QC_ROTOR_SIZE);
        g.drawOval(CANVAS_CENTER_X + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                CANVAS_CENTER_Y + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                QC_ROTOR_SIZE, QC_ROTOR_SIZE);
        g.drawOval(CANVAS_CENTER_X - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                CANVAS_CENTER_Y + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 2,
                QC_ROTOR_SIZE, QC_ROTOR_SIZE);
    }

    private void drawRotorState(Graphics g) {
        // rotor m0
        g.setColor(Color.RED);
        g.drawArc(CANVAS_CENTER_X - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                CANVAS_CENTER_Y - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE / 2, QC_ROTOR_START_ANGLE + 180,
                Math.round(-360 * (motorValue0 / 1000f)));
        // rotor m1
        g.setColor(Color.BLUE);
        g.drawArc(CANVAS_CENTER_X + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                CANVAS_CENTER_Y - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE / 2, QC_ROTOR_START_ANGLE,
                Math.round(360 * (motorValue1 / 1000f)));
        
        g.setColor(Color.RED);
        // rotor m2
        g.drawArc(CANVAS_CENTER_X + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                CANVAS_CENTER_Y + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE / 2, QC_ROTOR_START_ANGLE + 180,
                Math.round(-360 * (motorValue2 / 1000f)));
        // rotor m3
        g.setColor(Color.BLUE);
        g.drawArc(CANVAS_CENTER_X - QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                CANVAS_CENTER_Y + QC_ROTOR_DELTA - QC_ROTOR_SIZE / 4,
                QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE / 2, QC_ROTOR_START_ANGLE,
                Math.round(360 * (motorValue3 / 1000f)));
    }

    public synchronized void update(int m0, int m1, int m2, int m3) {
        motorValue0 = m0;
        motorValue1 = m1;
        motorValue2 = m2;
        motorValue3 = m3;
        this.repaint();
    }
}
