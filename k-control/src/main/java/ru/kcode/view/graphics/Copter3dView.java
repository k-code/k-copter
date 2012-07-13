package ru.kcode.view.graphics;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Copter3dView implements GLEventListener {
    private static final float QC_SHAFT_LENGTH = 1f;
    private static final float QC_SHAFT_WIDTH = 0.2f;
    private static final float QC_ROTOR_RADIUS = 0.5f;
    private static final float QC_SHAFT_LENGTH_DELTA = (float) Math.sqrt((QC_SHAFT_LENGTH * QC_SHAFT_LENGTH) / 2f);
    private static final float QC_SHAFT_WIDTH_DELTA = (float) Math.sqrt((QC_SHAFT_WIDTH * QC_SHAFT_WIDTH) / 2f);
    private static final float QC_ROTOR_DELTA = (float) Math.sqrt(
            ( (QC_SHAFT_LENGTH + QC_ROTOR_RADIUS) * (QC_SHAFT_LENGTH + QC_ROTOR_RADIUS) ) / 2f );
    private static final int angleIncrement = 5;
    
    @SuppressWarnings("unused") // used for debug
    private int angel = 0;

    private GLU glu;
    private int w, h;

    private int xAngle = 0;
    private int yAngle = 0;
    private int zAngle = 0;

    private int xCurentAngle = 0;
    private int yCurentAngle = 0;
    private int zCurentAngle = 0;

    @Override
    public void init(GLAutoDrawable drawable) {
        w = drawable.getWidth();
        h = drawable.getHeight();

        GL2 gl = (GL2) drawable.getGL();
        glu = new GLU();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

    }

    public void setXAngle(int angle) {
        xAngle = -angle;
    }

    public void setYAngle(int angle) {
        yAngle = -angle;
    }

    public void setZAngle(int angle) {
        zAngle = -angle;
    }
    
    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = (GL2) drawable.getGL();

        w = drawable.getWidth();
        h = drawable.getHeight();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, (float) w / (float) h, 0.1f, 100.0f);
        gl.glTranslatef(0f, 0f, -6f);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        
        drawScene(drawable);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        
    }

    private void drawScene(GLAutoDrawable drawable) {
        GL2 gl = (GL2) drawable.getGL();
        if (xCurentAngle > xAngle ) {
            xCurentAngle -= angleIncrement;
        }
        if (xCurentAngle < xAngle ) {
            xCurentAngle += angleIncrement;
        }
        if (yCurentAngle > yAngle ) {
            yCurentAngle -= angleIncrement;
        }
        if (yCurentAngle < yAngle ) {
            yCurentAngle += angleIncrement;
        }
        if (zCurentAngle > zAngle ) {
            zCurentAngle -= angleIncrement;
        }
        if (zCurentAngle < zAngle ) {
            zCurentAngle += angleIncrement;
        }

        gl.glRotatef(xCurentAngle, 1f, 0f, 0f);
        gl.glRotatef(yCurentAngle, 0f, 1f, 0f);
        gl.glRotatef(zCurentAngle, 0f, 0f, 1f);
        //gl.glRotatef(angel++, 1f, 1f, 1f);

        drawCopter(drawable);
    }

    private void drawCopter(GLAutoDrawable drawable) {
        drawAxis(drawable);
        
        drawShaft(drawable, -1, -1);
        drawProp(drawable, -1, -1);
        
        drawShaft(drawable, 1, -1);
        drawProp(drawable, 1, -1);
        
        drawShaft(drawable, -1, 1);
        drawProp(drawable, -1, 1);
        
        drawShaft(drawable, 1, 1);
        drawProp(drawable, 1, 1);
    }

    private void drawAxis(GLAutoDrawable drawable) {
        GL2 gl = (GL2) drawable.getGL();
        double cx = 0;
        double cy = 0;
        double cz = 0;

        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor4f(1, 0, 0, 1);
        gl.glVertex3d(cx-0.2, cy, cz);
        gl.glVertex3d(cx+1, cy, cz);
        gl.glEnd();
        
        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor4f(0, 1, 0, 1);
        gl.glVertex3d(cx, cy-0.2, cz);
        gl.glVertex3d(cx, cy+1, cz);
        gl.glEnd();
        
        gl.glBegin(GL2.GL_LINE_STRIP);
        gl.glColor4f(0, 0, 1, 1);
        gl.glVertex3d(cx, cy, cz-0.2);
        gl.glVertex3d(cx, cy, cz+1);
        gl.glEnd();

    }
    
    private void drawProp(GLAutoDrawable drawable, float xShift, float zShift) {
        GL2 gl = (GL2) drawable.getGL();
        float increment = (float) (2 * Math.PI / 50);
        float cx = 0;
        float cy = 0;
        float cz = 0;
        float radius = QC_ROTOR_RADIUS;
        
        if (zShift > 0) {
            gl.glColor4f(0, 0, 1, 1);
        }
        else {
            gl.glColor4f(1, 0, 0, 1);
        }

        for (float angle = 0; angle < 2 * Math.PI; angle += increment) {
            
            gl.glBegin(GL2.GL_POLYGON);
            
            gl.glVertex3d(cx + Math.cos(angle) * radius+(QC_ROTOR_DELTA)*xShift,
                    cy - QC_SHAFT_WIDTH,
                    cz + Math.sin(angle) * radius+(QC_ROTOR_DELTA)*zShift);
            gl.glVertex3d(cx + Math.cos(angle) * radius+(QC_ROTOR_DELTA)*xShift,
                    cy + QC_SHAFT_WIDTH,
                    cz + Math.sin(angle) * radius+(QC_ROTOR_DELTA)*zShift);
            gl.glVertex3d(cx + Math.cos(angle + increment) * radius+(QC_ROTOR_DELTA)*xShift,
                    cy + QC_SHAFT_WIDTH,
                    cz + Math.sin(angle + increment) * radius+(QC_ROTOR_DELTA)*zShift);
            gl.glVertex3d(cx + Math.cos(angle + increment) * radius+(QC_ROTOR_DELTA)*xShift,
                    cy - QC_SHAFT_WIDTH,
                    cz + Math.sin(angle + increment) * radius+(QC_ROTOR_DELTA)*zShift);

            gl.glEnd();
        }
    }

    private void drawShaft(GLAutoDrawable drawable, float xShift, float zShift) {
        GL2 gl = (GL2) drawable.getGL();
        float cx = 0;
        float cy = 0;
        float cz = 0;
        if (zShift > 0) {
            gl.glColor4f(0, 0, 1, 1);
        }
        else {
            gl.glColor4f(1, 0, 0, 1);
        }

        gl.glBegin(GL2.GL_QUADS);

        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA)*xShift, cy+QC_SHAFT_WIDTH/2, cz);
        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA+QC_SHAFT_LENGTH_DELTA)*xShift, cy+QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA)*zShift);
        gl.glVertex3d(cx+(QC_SHAFT_LENGTH_DELTA)*xShift, cy+QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA+QC_SHAFT_WIDTH_DELTA)*zShift);
        gl.glVertex3d(cx, cy+QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_WIDTH_DELTA)*zShift);
        
        gl.glVertex3d(cx, cy+QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_WIDTH_DELTA)*zShift);
        gl.glVertex3d(cx+(QC_SHAFT_LENGTH_DELTA)*xShift, cy+QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA+QC_SHAFT_WIDTH_DELTA)*zShift);
        gl.glVertex3d(cx+(QC_SHAFT_LENGTH_DELTA)*xShift, cy-QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA+QC_SHAFT_WIDTH_DELTA)*zShift);
        gl.glVertex3d(cx, cy-QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_WIDTH_DELTA)*zShift);
        
        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA)*xShift, cy-QC_SHAFT_WIDTH/2, cz);
        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA+QC_SHAFT_LENGTH_DELTA)*xShift, cy-QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA)*zShift);
        gl.glVertex3d(cx+(QC_SHAFT_LENGTH_DELTA)*xShift, cy-QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA+QC_SHAFT_WIDTH_DELTA)*zShift);
        gl.glVertex3d(cx, cy-QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_WIDTH_DELTA)*zShift);

        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA)*xShift, cy+QC_SHAFT_WIDTH/2, cz);
        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA+QC_SHAFT_LENGTH_DELTA)*xShift, cy+QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA)*zShift);
        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA+QC_SHAFT_LENGTH_DELTA)*xShift, cy-QC_SHAFT_WIDTH/2, cz+(QC_SHAFT_LENGTH_DELTA)*zShift);
        gl.glVertex3d(cx+(QC_SHAFT_WIDTH_DELTA)*xShift, cy-QC_SHAFT_WIDTH/2, cz);

        gl.glEnd();
    }
}
