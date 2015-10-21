/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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
