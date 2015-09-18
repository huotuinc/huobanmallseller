package com.huotu.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/9/17.
 */
public class HttpHelper {

    public static String getRequest(String url,String data) throws IOException {
        URL u = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("content-type", "text/html;charset=UTF-8");
        urlConnection.connect();

        // 获取URLConnection对象对应的输出流
      PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
        // 发送请求参数
        out.print(data);
        // flush输出流的缓冲
        out.flush();

        int statusCode = urlConnection.getResponseCode();



        if (statusCode == 200) {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
                String str = reader.readLine();
                while (str != null) {
                    stringBuilder.append(str);
                    str = reader.readLine();
                }
            }
            return stringBuilder.toString();
        } else {
            throw new IOException();
        }
    }
}
