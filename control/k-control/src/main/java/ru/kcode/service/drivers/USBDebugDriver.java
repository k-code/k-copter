package ru.kcode.service.drivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kcode.service.RelationsController;
import ru.kcode.service.Utils;
import ru.kcode.service.protocol.Protocol;

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
        while (isRun) {
            byte buf[] = new byte[Protocol.MAX_LENGTH];
            int len = 0;
            try {
                int available = reader.available();

                //mast be available control frame, package length, package number and frames
                if (available > 12) {
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
                
                if ( p.getLen() > 1 ) {
                    String format = String.format("%d", p.getFrames().get(0).getData());
                    //log.debug(format);
                    System.out.println(format);
                    RelationsController.getCopter3dView().setXAngle(p.getFrames().get(0).getData() / 10);
                    RelationsController.getCopter3dView().setZAngle(p.getFrames().get(1).getData() / 10);
                }
                else {/*
                    String str = new String();
                    for (int i=0; i < len; i++) {
                        str = String.format("%s%02x ", str, (byte)buf[i]);
                    }
                    log.debug("Recv data: {}", str);*/
                    System.out.println("bug");
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
