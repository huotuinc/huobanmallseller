package com.huotu.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/17.
 */
public class HttpHelper {
    /**
     * http post请求
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    public static String postRequest(String url,Map<String,String> data) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        BasicNameValuePair[] basicNameValuePairs=new BasicNameValuePair[data.size()];
        int i=0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            basicNameValuePairs[i]=new BasicNameValuePair(entry.getKey(),entry.getValue());
            i++;
        }
        post.setEntity(
                EntityBuilder.create()
                        .setContentEncoding("UTF-8")
                        .setContentType(ContentType.APPLICATION_FORM_URLENCODED)
                        .setParameters(basicNameValuePairs
                        )
                        .build()
        );
        HttpResponse resultData=httpClient.execute(post);
        InputStream is = resultData.getEntity().getContent();
        return new BufferedReader(new InputStreamReader(is,"UTF-8")).readLine();

    }

}
