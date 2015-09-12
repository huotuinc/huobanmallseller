package com.huotu.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lgh on 2015/9/1.
 */
public class DateHelper {

    /**
     * 获取本周第一天 (整点 凌晨)
     *
     * @return
     */
    public static Date getThisWeekBegin() {
        int mondayPlus;
        Calendar calendar = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }

        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        currentDate.set(Calendar.HOUR, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        return currentDate.getTime();
    }

    /**
     * 获取本月第一天 (整点 凌晨)
     *
     * @return
     */
    public static Date getThisMonthBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    /**
     * 获取当天整点
     * @return
     */
    public static Date getThisDayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
