package ru.kcode.service.protocol;

import ru.kcode.service.Utils;

public class Frame {
    public static final byte MOTOR_1 = 0x01;
    public static final byte MOTOR_2 = 0x02;
    public static final byte MOTOR_3 = 0x03;
    public static final byte MOTOR_4 = 0x04;
    
    public static final byte ANGEL_X = 0x05;
    public static final byte ANGEL_Y = 0x06;
    public static final byte ANGEL_Z = 0x07;
    public static final byte ANGEL_R = 0x08;
    
    public static final byte TYPE_BYTE = 0x01;
    public static final byte TYPE_INT = 0x02;
    public static final byte TYPE_STR = 0x04;
    
    private byte cmd;
    private byte type;
    private int data;
    
    static public Frame getFrame(byte[] buf, int offset) {
        if (buf.length < offset + 3) {
            return null;
        }
        Frame f = new Frame();
        f.cmd = buf[offset++];
        f.type = buf[offset++];
        
        switch (f.type) {
            case Frame.TYPE_BYTE:
                f.data = buf[offset];
                break;
            case Frame.TYPE_INT:
                f.data = Utils.parseInt(buf, offset);
                break;
    
            default:
                break;
        }
        return f;
    }
    
    public byte getCmd() {
        return cmd;
    }
    
    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }
    
    public byte getType() {
        return type;
    }
    
    public void setType(byte type) {
        this.type = type;
    }
    
    public int getData() {
        return data;
    }
    
    public void setData(int data) {
        this.data = data;
    }
}
