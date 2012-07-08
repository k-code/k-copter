package ru.kcode.service.drivers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import ru.kcode.service.MotorComputing;

public class USBDebugDriver extends DeviceDriver {
    private static final String NAME = "USB Driver (debug)";
    private static final String DEVICE_NAME = "/dev/kcopter";
    
    private OutputStreamWriter writer;

    @Override
    public void sendData(MotorComputing mc) {
        if (mc.getMotor0() > mc.getMotor1()) {
            sendData('s');
        }
        else {
            sendData('a');
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    private OutputStreamWriter getWriter() {
        File device = new File(DEVICE_NAME);
        
        if ( !device.exists() ){
            System.out.printf("File %s not exists\n", device);
            return null;
        }
        else if ( !device.canWrite() ) {
            System.out.printf("Can not wrte to file %s\n", device);
            return null;
        }
        
        OutputStream stmWriter;
        try {
            stmWriter = new FileOutputStream(device);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        OutputStreamWriter writer = new OutputStreamWriter(stmWriter);
        
        return writer;
    }
    
    public void sendData(char s) {
        if (writer == null) {
            return;
        }
        try {
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        writer = getWriter();
    }

    @Override
    public void stop() {
        try {
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
