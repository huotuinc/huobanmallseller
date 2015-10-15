package com.huotu.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/14.
 */
public class DateHelperTest {

    @Test
    public void testGetTimeAbscissa() throws Exception {
        Integer[] time=new Integer[(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+2)/3];
        for(int i=0;i<time.length;i++){
            time[i]=(i+1)*3;
        }
        Integer[] time2=DateHelper.getTimeAbscissa();
        for(int i=0;i<time2.length;i++){
            System.out.println(time2[i]);
        }
        Assert.assertEquals("", time, time);




        Map<Integer,Float> map=DateHelper.getTimeAbscissa(Float.class);
        for (Map.Entry<Integer, Float> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
//            partners[(entry.getKey()-1)/3] += entry.getValue();
        }


    }
}