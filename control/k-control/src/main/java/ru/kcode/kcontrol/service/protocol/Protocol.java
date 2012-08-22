package ru.kcode.kcontrol.service.protocol;

import java.util.ArrayList;
import java.util.List;

import ru.kcode.kcontrol.service.Utils;

public class Protocol {
    public static final int MAX_LENGTH = 32;

    private static int num = 0;
    
    private int len = 0;
    private byte[] mess = new byte[MAX_LENGTH];
    private List<Frame> frames; 

    public Protocol() {
        frames = new ArrayList<Frame>();
        len+=4;
        num++;
        addInt(num);
        addLenToMess();
    }

    public Protocol(byte[] buf, int len) {
        frames = new ArrayList<Frame>();
        parseProtocol(buf, len);
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
        arrayCopy(mess, Utils.itba(val), len, 4);
        len+=4;
    }
    
    private void arrayCopy(byte[] to, byte from[], int pos, int len) {
        for (int i=pos, j=0; i < pos+len; i++, j++) {
            to[i] = from[j];
        }
    }
    
    private void addLenToMess() {
        arrayCopy(mess, Utils.itba(len), 0, 4);
    }
    
    private void parseProtocol(byte[] buf, int len) {
        Frame f;
        for (int i = 4; i < len; ) {
            f = Frame.getFrame(buf, i);
            switch (f.getType()) {
                case Frame.TYPE_BYTE : i+= 3; break;
                case Frame.TYPE_INT : i+= 6; break;
                default: return;
            }
            this.addFrame(f);
            this.len++;
        }
    }
    
    private void addFrame(Frame f) {
        if (frames == null) {
            frames = new ArrayList<Frame>();
        }
        this.frames.add(f);
    }


}
