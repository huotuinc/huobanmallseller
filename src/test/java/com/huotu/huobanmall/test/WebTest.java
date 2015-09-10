package com.huotu.huobanmall.test;

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

}
