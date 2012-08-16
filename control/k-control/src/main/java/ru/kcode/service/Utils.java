package ru.kcode.service;

public class Utils {

    
    public static int parseInt(byte[] buf, int offset) {
        int res = ((int)buf[offset++]) << 24;
        res |= ((int)buf[offset++]) << 16;
        res |= ((int)buf[offset++]) << 8;
        res |= ((int)buf[offset]);

        return res;
    }
    
    /**
     * Integer to byte array
     * @param value
     * @return
     */
    public static byte[] itba(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
    
}
