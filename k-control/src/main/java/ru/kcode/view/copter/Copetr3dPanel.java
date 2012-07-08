package ru.kcode.view.copter;

import java.awt.Dimension;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;

public class Copetr3dPanel extends JPanel {
    private static final long serialVersionUID = 2219431191224761363L;
    private static final int FPS = 80;
    
    private FPSAnimator animator;
    private GLCanvas canvas;
    private Copter3dView copter3d;
    
    public Copetr3dPanel() {
        
        initComponents();

        animator = new FPSAnimator(FPS);
        copter3d = new Copter3dView();

        canvas.addGLEventListener(copter3d);
        
        animator.add(canvas);


        // This is a workaround for the GLCanvas not adjusting its size, when the frame is resized.
        canvas.setMinimumSize(new Dimension());  
        animator.start();       
        
    }
    
    private void initComponents() {
        jogamp.common.Debug.debugAll();
        canvas = new GLCanvas(createGLCapabilites());
        canvas.setBounds(10, 10, 400, 400);
        add(canvas);
    }
    
    private GLCapabilities createGLCapabilites() {
        GLCapabilities capabilities = new GLCapabilities(GLProfile.getGL2GL3());
        capabilities.setHardwareAccelerated(true);

        // try to enable 2x anti aliasing - should be supported on most hardware
        capabilities.setNumSamples(2);
        capabilities.setSampleBuffers(true);
        
        return capabilities;
    }
    
    public Copter3dView getCopter3dView() {
        return copter3d;
    }
}
