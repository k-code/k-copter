package ru.kcode.kcontrol.view.graphics;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import ru.kcode.kcontrol.view.GBLHelper;
import ru.kcode.kcontrol.view.panels.GraphicPanel;

import com.jogamp.opengl.util.FPSAnimator;

public class Copter3dPanel extends GraphicPanel {
    private static final long serialVersionUID = 2219431191224761363L;
    private static final int FPS = 80;
    
    private FPSAnimator animator;
    private GLCanvas canvas;
    private Copter3dView copter3d;
    
    public Copter3dPanel() {
        super();
        name = "3D simulyator";
        setLayout(new GridBagLayout());

        canvas = new GLCanvas(createGLCapabilites());
        add(canvas, GBLHelper.create().fillB());
        
        animator = new FPSAnimator(FPS);
        copter3d = new Copter3dView();

        canvas.addGLEventListener(copter3d);
        
        animator.add(canvas);

        canvas.setMinimumSize(new Dimension());  
        animator.start();
    }
    
    private GLCapabilities createGLCapabilites() {
        GLCapabilities capabilities = new GLCapabilities(GLProfile.getGL2GL3());
        capabilities.setHardwareAccelerated(true);

        capabilities.setNumSamples(2);
        capabilities.setSampleBuffers(true);
        
        return capabilities;
    }
    
    public Copter3dView getCopter3dView() {
        return copter3d;
    }
}
