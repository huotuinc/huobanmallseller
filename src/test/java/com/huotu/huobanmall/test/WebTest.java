/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.test;

import com.huotu.huobanmall.entity.Merchant;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lgh on 2015/8/27.
 */
public class WebTest {
    @Test
    public void test1(){
        Map<Integer,String> map=new TreeMap<Integer,String>();
        map.put(6,"5");
        map.put(2,"6");
        map.put(3,"7");
        map.put(96,"3");
        map.put(14,"11");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

    }

    @Test
    public void test2(){
        Merchant merchant=new Merchant();
        merchant.setName("123");
        merchant.setMobile("1234156789");
        List<Merchant> merchants=new ArrayList<>();
        merchants.add(merchant);
        System.out.print(merchants.toString());




    }

    @Test
    public void test3(){

        //p12文件路径
        String keyPath = "C:\\Users\\Administrator\\IdeaProjects\\huobanmall\\target\\classes\\com\\huotu\\huobanmall\\service\\Push.Development.p12";
        //p12文件密匙
        String password = "123456";
        //创建一个APNS service
        ApnsService service = APNS.newService()
                .withCert(keyPath, password)	//使用指定的p12文件以及密匙
                .withSandboxDestination()	//使用apple的测试服务器
                .build();

        //token由客户端获取
        String token = "45124a4cf9f5e272d395f6392456e5ab7185d2ae6e98ba2f8426fe09f60e785b";
        //发送消息到iOS设备
        service.push(token, pushStr());

//        InputStream input = MessageService.class.getResourceAsStream("Push.Development.p12");
//        ApnsServiceBuilder builder = APNS.newService().withCert(input, "123456");
    }

    private String pushStr(){
        String payload = APNS.newPayload()
                .alertBody("hello world!")	//推送通知显示的文字
                .sound("default")	//推送时附带的声音提示
                .badge(1)	//应用程序图标右上角显示的数字
                .build();

        return payload;
    }


}
