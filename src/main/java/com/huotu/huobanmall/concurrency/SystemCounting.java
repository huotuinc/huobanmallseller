package com.huotu.huobanmall.concurrency;

import java.text.ParseException;

/**
 * Created by lgh on 2015/9/12.
 */
public interface SystemCounting {
    void count();

    void countDay();

    void InitHistoryDayAndToday() throws ParseException;
}
