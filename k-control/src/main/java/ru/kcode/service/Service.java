package ru.kcode.service;

import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;

public class Service {

    public static CharBuffer run() throws Exception {

        // TODO Auto-generated method stub

        File stmDev = new File("/dev/ttyACM0");

        out.println("File: " + stmDev.getPath());
        out.println("Is exists: " + stmDev.exists());
        out.println("Can read: " + stmDev.canRead());
        out.println("Can write: " + stmDev.canWrite());

        OutputStream stmWriter = new FileOutputStream(stmDev);
        OutputStreamWriter writer = new OutputStreamWriter(stmWriter);
        writer.write('s');
        writer.flush();

        CharBuffer buf = CharBuffer.allocate(32);
        /*
         * InputStream input = new FileInputStream(stmDev); InputStreamReader
         * reader = new InputStreamReader(input); out.println("read: " +
         * reader.read(buf));F buf.position(0); out.printf("buf: %s\n",
         * buf.toString());
         */

        File hidDev = new File("/dev/hidraw2");
        InputStream input2 = new FileInputStream(hidDev);
        InputStreamReader reader2 = new InputStreamReader(input2);
        reader2.read(buf);
        /*
         * for (int i=0; i < 100; i++) { out.printf("read: %s\n",
         * reader2.read(buf)); buf.position(0); for (char c : buf.array()) {
         * out.printf("%d ", Integer.valueOf(c)); } }
         */
        return buf;
    }
}
