package ru.kcode.kcontrol.service.drivers;

import java.io.*;

import gnu.io.NRSerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.kcode.kcontrol.service.RelationsController;
import ru.kcode.kcontrol.service.Utils;
import ru.kcode.kcontrol.service.protocol.Frame;
import ru.kcode.kcontrol.service.protocol.Protocol;

public class USBDebugDriver extends DeviceDriver implements Runnable {
    private static final String NAME = "USB Driver (debug)";
    private Logger log = LoggerFactory.getLogger(USBDebugDriver.class);

    private DataOutputStream writer;
    private DataInputStream reader;
    private boolean isRun = false;

    @Override
    public synchronized void sendData(Protocol p) {
        super.sendData(p);
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
        NRSerialPort serial = null;
        for (String port: NRSerialPort.getAvailableSerialPorts()) {
            serial = new NRSerialPort(port, 19200);
            break;
        }
        if (serial == null) {
            return;
        }
        serial.connect();

        writer = getWriter(serial);
        reader = getReader(serial);
        isRun = true;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void stop() {
        if (!isRun) {
            return;
        }
        try {
            isRun = false;
            writer.close();
            reader.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private DataOutputStream getWriter(NRSerialPort serial) {
        DataOutputStream stmWriter = new DataOutputStream(serial.getOutputStream());
        
        return stmWriter;
    }
    
    private DataInputStream getReader(NRSerialPort serial) {
        DataInputStream stmReader = new DataInputStream(serial.getInputStream());

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
                    //log.debug("Available bytes: {}", available);
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
                super.receiveData(p);
                
                for (Frame f : p.getFrames()) {
                    switch (f.getCmd()) {
                    case Frame.ANGEL_X:
                        accx = (int)(((f.getData()-1)*100)*k + (accx)*(1-k));
                        RelationsController.getCopter3dView().setXAngle(accx/100);
                        System.out.println(accx/100);
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
