package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * 销售额统计数据
 * Created by Administrator on 2015/8/24.
 */
public class AppSalesReportListModel {
    /**
     * 时间
     */
    private Date time;

    /**
     * 金额
     */
    private float money;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
