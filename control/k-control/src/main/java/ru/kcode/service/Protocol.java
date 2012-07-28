package ru.kcode.service;

public class Protocol {
    public static final byte MOTOR_1 = 0x01;
    public static final byte MOTOR_2 = 0x02;
    public static final byte MOTOR_3 = 0x03;
    public static final byte MOTOR_4 = 0x04;
    public static final byte ANGEL_X = 0x05;
    public static final byte ANGEL_Y = 0x06;
    public static final byte ANGEL_Z = 0x07;
    public static final byte ANGEL_R = 0x08;
    public static final byte MESSAGE = 0x09;
    
    public static final int MAX_LENGTH = 255;
    private static final byte TYPE_byte = 0x01;
    private static final byte TYPE_INT = 0x02;
    private static final byte TYPE_STR = 0x04;

    private static int num = 0;
    
    private int len = 0;
    private byte[] mess = new byte[MAX_LENGTH];
    
    public Protocol() {
        len+=4;
        //num++;
        addInt(num);
        addLenToMess();
    }

    public void addParam(byte id, byte val) {
        addbyte(id);
        addbyte(TYPE_byte);
        addInt(val);
    }

    public void addParam(byte id, int val) {
        addbyte(id);
        addbyte(TYPE_INT);
        addInt(val);
    }
    
    public void addParam(byte id, String val) {
        addbyte(id);
        addbyte(TYPE_STR);
        addString(val);
    }
    
    public byte[] getMess() {
        addLenToMess();
        return mess;
    }
    
    public int getLen() {
        return len;
    }
    
    private void addbyte(byte val) {
        mess[len] = val;
        len++;
    }
    
    private void addInt(int val) {
        arrayCopy(mess, itba(val), len, 4);
        len+=4;
    }
    
    private void addString(String val) {
        int strlen = val.length();
        arrayCopy(mess, val.getBytes(), len, strlen);
        len+= strlen;
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
    
    @Override
    public String toString() {
        addLenToMess();
        StringBuffer str = new StringBuffer();
        for (int i=0; i < len; i++) {
            str.append(String.format("%02x ", (byte)mess[i]));
        }
        return str.toString();
    }
}
