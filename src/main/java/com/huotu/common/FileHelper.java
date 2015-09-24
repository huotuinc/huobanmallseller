package com.huotu.common;

import java.io.*;

/**
 * Created by lgh on 2015/9/24.
 */
public class FileHelper {

    public static byte[] getBytes(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int n = 0;
        while ((n = fileInputStream.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, n);
        }
        byteArrayOutputStream.close();
        fileInputStream.close();

        return byteArrayOutputStream.toByteArray();
    }
}
