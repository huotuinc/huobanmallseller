/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.test;

import com.huotu.common.MathHelper;
import org.junit.Test;

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
//        BigDecimal bg = new BigDecimal(1.300);
//        double countDodaySales = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        System.out.print(countDodaySales);
        Double n=200.0+0.23;
        System.out.print(MathHelper.retainDecimal(n,2));


//        System.out.println(DateHelper.getThisDayBegin());
//
//        Date date = DateHelper.getThisDayBegin();
//        date.setHours(-24);
//        System.out.println(date);

    }

}
