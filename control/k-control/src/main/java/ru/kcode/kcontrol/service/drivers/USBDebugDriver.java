package ru.kcode.kcontrol.service.drivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kcode.kcontrol.service.RelationsController;
import ru.kcode.kcontrol.service.Utils;
import ru.kcode.kcontrol.service.protocol.Frame;
import ru.kcode.kcontrol.service.protocol.Protocol;

public class USBDebugDriver extends DeviceDriver implements Runnable {
    private static final String NAME = "USB Driver (debug)";
    private static final String DEVICE_NAME = "/dev/kcopter";
    private Logger log = LoggerFactory.getLogger(USBDebugDriver.class);

    private FileOutputStream writer;
    private FileInputStream reader;
    private boolean isRun = false;

    @Override
    public void sendData(Protocol p) {
        if (writer == null) {
            return;
        }
        try {
            byte[] mess = p.getMess();
            writer.write(mess, 0, p.getLen());
            writer.flush();
            log.debug("Send data: {}", p.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void start() {
        writer = getWriter();
        reader = getReader();
        isRun = true;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void stop() {
        try {
            isRun = false;
            writer.close();
            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private FileOutputStream getWriter() {
        File device = new File(DEVICE_NAME);
        
        if ( !device.exists() ){
            System.out.printf("File %s not exists\n", device);
            return null;
        }
        else if ( !device.canWrite() ) {
            System.out.printf("Can not wrte to file %s\n", device);
            return null;
        }
        
        FileOutputStream stmWriter;
        try {
            stmWriter = new FileOutputStream(device);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
        return stmWriter;
    }
    
    private FileInputStream getReader() {
        File device = new File(DEVICE_NAME);
        
        if ( !device.exists() ){
            System.out.printf("File %s not exists\n", device);
            return null;
        }
        else if ( !device.canRead() ) {
            System.out.printf("Can not wrte to file %s\n", device);
            return null;
        }
        
        FileInputStream stmReader;
        try {
            stmReader = new FileInputStream(device);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
        return stmReader;
    }

    @Override
    public void run() {
        byte buf[] = new byte[Protocol.MAX_LENGTH];
        int len = 0;
        double k = 0.15;
        int accx = 0;
        int accz = 0;
        while (isRun) {
            try {
                int available = reader.available();

                //mast be available control frame (4B), package length (4B) and package number (4B)
                if (available >= 12) {
                    //log.debug("Avaible bytes: {}", available);
                    len = getPackageLength();
                    if ( len <= 0 ) {
                        continue;
                    }
                }
                else {
                    continue;
                }
                len = reader.read(buf, 0, len-8);
                Protocol p = new Protocol(buf, len);
                
                for (Frame f : p.getFrames()) {
                    switch (f.getCmd()) {
                    case Frame.ANGEL_X:
                        accx = (int)(f.getData()*k + accx*(1-k));
                        RelationsController.getCopter3dView().setXAngle(accx);
                        //System.out.println(accx);
                        break;
                    case Frame.ANGEL_Y:
                        accz = (int)(f.getData()*k + accz*(1-k));
                        RelationsController.getCopter3dView().setZAngle(accz);
                        break;
                    default:
                        break;
                    }
                }
                
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                isRun = false;
            } catch (IndexOutOfBoundsException e) {
                System.out.println(len);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                isRun = false;
            }
        }
    }
    
    private int getPackageLength() throws IOException {
        byte controlFrame[] = new byte[4];
        do {
            if (reader.available() >= 8) {
                reader.read(controlFrame, 0, 4);
                if (Utils.parseInt(controlFrame, 0) == 0x55555555) {
                    reader.read(controlFrame, 0, 4);
                    return Utils.parseInt(controlFrame, 0);
                }
            }
            else {
                return 0;
            }
        } while(reader.available() >= 4);
        return 0;
    }
}
