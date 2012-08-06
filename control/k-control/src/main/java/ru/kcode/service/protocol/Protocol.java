package ru.kcode.service.protocol;

import java.util.ArrayList;
import java.util.List;

public class Protocol {
    public static final int MAX_LENGTH = 255;

    private static int num = 0;
    
    private int len = 0;
    private byte[] mess = new byte[MAX_LENGTH];
    private List<Frame> frames; 

    public Protocol() {
        len+=4;
        num++;
        addInt(num);
        addLenToMess();
    }

    public Protocol(byte[] buf) {
        parseProtocol(buf);
    }

    public void addParam(byte id, byte val) {
        addbyte(id);
        addbyte(Frame.TYPE_BYTE);
        addInt(val);
    }

    public void addParam(byte id, int val) {
        addbyte(id);
        addbyte(Frame.TYPE_INT);
        addInt(val);
    }
    
    public byte[] getMess() {
        addLenToMess();
        return mess;
    }
    
    public int getLen() {
        return len;
    }
    
    public List<Frame> getFrames() {
        return frames;
    }
    
    @Override
    public String toString() {
        addLenToMess();
        StringBuffer str = new StringBuffer();
        for (int i=0; i < len; i++) {
            str.append(String.format("%02x ", (byte)mess[i]));
        }
        return str.toString();
    }
    
    private void addbyte(byte val) {
        mess[len] = val;
        len++;
    }
    
    private void addInt(int val) {
        arrayCopy(mess, itba(val), len, 4);
        len+=4;
    }
    
    /**
     * Integer to byte array
     * @param value
     * @return
     */
    private byte[] itba(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
    
    private void arrayCopy(byte[] to, byte from[], int pos, int len) {
        for (int i=pos, j=0; i < pos+len; i++, j++) {
            to[i] = from[j];
        }
    }
    
    private void addLenToMess() {
        arrayCopy(mess, itba(len), 0, 4);
    }
    
    private void parseProtocol(byte[] buf) {
        Frame f;
        int len = parseInt(buf, 0);
        for (int i = 8; i < len; ) {
            f = Frame.getFrame(buf, i);
            switch (f.getType()) {
                case Frame.TYPE_BYTE : i+= 3; break;
                case Frame.TYPE_INT : i+= 6; break;
                default: return;
            }
            this.addFrame(f);
            this.len = i;
        }
    }
    
    private void addFrame(Frame f) {
        if (frames == null) {
            frames = new ArrayList<Frame>();
        }
        this.frames.add(f);
    }

    private static int parseInt(byte[] buf, int offset) {
        int res = (int)(buf[offset++] << 24);
        res += (int)(buf[offset++] << 16);
        res += (int)(buf[offset++] << 8);
        res += (int)(buf[offset]);

        return res;
    }

}
