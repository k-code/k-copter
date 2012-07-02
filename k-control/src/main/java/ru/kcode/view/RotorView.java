package ru.kcode.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class RotorView extends Canvas {
    private static final int CANVAS_WIDTH = 200;
    private static final int CANVAS_HEIGHT = 200;
    private static final int CANVAS_CENTER_X = CANVAS_WIDTH / 2;
    private static final int CANVAS_CENTER_Y = CANVAS_HEIGHT / 2;
    private static final int QC_SHAFT_LENGTH = 30;
    private static final int QC_ROTOR_SIZE = 60;

    private int motorValue0 = 700;
    private int motorValue1 = 400;
    private int motorValue2 = 530;
    private int motorValue3 = 850;

    private static final long serialVersionUID = -6577075623577413434L;

    public RotorView() {
        super();
        this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.setBackground(Color.WHITE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drowQuadroCopter(g);
        drawRotorState(g);
    }

    private void drowQuadroCopter(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(CANVAS_CENTER_X - QC_SHAFT_LENGTH, CANVAS_CENTER_Y,
                CANVAS_CENTER_X + QC_SHAFT_LENGTH, CANVAS_CENTER_Y);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y - QC_SHAFT_LENGTH,
                CANVAS_CENTER_X, CANVAS_CENTER_Y + QC_SHAFT_LENGTH);

        g.setColor(Color.RED);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y, CANVAS_CENTER_X,
                CANVAS_CENTER_Y - QC_SHAFT_LENGTH);
        g.setColor(Color.BLACK);

        g.drawOval(CANVAS_CENTER_X - QC_ROTOR_SIZE / 2, CANVAS_CENTER_Y
                - QC_SHAFT_LENGTH - QC_ROTOR_SIZE, QC_ROTOR_SIZE, QC_ROTOR_SIZE);
        g.drawOval(CANVAS_CENTER_X + QC_SHAFT_LENGTH, CANVAS_CENTER_Y
                - QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE, QC_ROTOR_SIZE);
        g.drawOval(CANVAS_CENTER_X - QC_ROTOR_SIZE / 2, CANVAS_CENTER_Y
                + QC_SHAFT_LENGTH, QC_ROTOR_SIZE, QC_ROTOR_SIZE);
        g.drawOval(CANVAS_CENTER_X - QC_SHAFT_LENGTH - QC_ROTOR_SIZE,
                CANVAS_CENTER_Y - QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE,
                QC_ROTOR_SIZE);

        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y - QC_SHAFT_LENGTH
                - QC_ROTOR_SIZE / 2, CANVAS_CENTER_X, CANVAS_CENTER_Y
                - QC_SHAFT_LENGTH - QC_ROTOR_SIZE / 2);
        g.drawLine(CANVAS_CENTER_X + QC_SHAFT_LENGTH + QC_ROTOR_SIZE / 2,
                CANVAS_CENTER_Y, CANVAS_CENTER_X + QC_SHAFT_LENGTH
                        + QC_ROTOR_SIZE / 2, CANVAS_CENTER_Y);
        g.drawLine(CANVAS_CENTER_X, CANVAS_CENTER_Y + QC_SHAFT_LENGTH
                + QC_ROTOR_SIZE / 2, CANVAS_CENTER_X, CANVAS_CENTER_Y
                + QC_SHAFT_LENGTH + QC_ROTOR_SIZE / 2);
        g.drawLine(CANVAS_CENTER_X - QC_SHAFT_LENGTH - QC_ROTOR_SIZE / 2,
                CANVAS_CENTER_Y, CANVAS_CENTER_X - QC_SHAFT_LENGTH
                        - QC_ROTOR_SIZE / 2, CANVAS_CENTER_Y);
    }

    private void drawRotorState(Graphics g) {
        g.setColor(Color.RED);
        g.drawArc(CANVAS_CENTER_X - QC_ROTOR_SIZE / 4, CANVAS_CENTER_Y
                - QC_SHAFT_LENGTH - QC_ROTOR_SIZE + QC_ROTOR_SIZE / 4,
                QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE / 2, 90,
                Math.round(360 * (motorValue0 / 1000f)));
        g.drawArc(CANVAS_CENTER_X - QC_ROTOR_SIZE / 4, CANVAS_CENTER_Y
                + QC_SHAFT_LENGTH + QC_ROTOR_SIZE / 4, QC_ROTOR_SIZE / 2,
                QC_ROTOR_SIZE / 2, 90, Math.round(360 * (motorValue2 / 1000f)));
        g.setColor(Color.BLUE);
        g.drawArc(CANVAS_CENTER_X - QC_SHAFT_LENGTH - QC_ROTOR_SIZE
                + QC_ROTOR_SIZE / 4, CANVAS_CENTER_Y - QC_ROTOR_SIZE / 4,
                QC_ROTOR_SIZE / 2, QC_ROTOR_SIZE / 2, 90,
                Math.round(360 * (motorValue1 / 1000f)));
        g.drawArc(CANVAS_CENTER_X + QC_SHAFT_LENGTH + QC_ROTOR_SIZE / 4,
                CANVAS_CENTER_Y - QC_ROTOR_SIZE / 4, QC_ROTOR_SIZE / 2,
                QC_ROTOR_SIZE / 2, 90, Math.round(360 * (motorValue3 / 1000f)));
    }
    
    public void repaint(int m0, int m1, int m2, int m3) {
        motorValue0 = m0;
        motorValue1 = m1;
        motorValue2 = m2;
        motorValue3 = m3;
        this.repaint();
    }
}
