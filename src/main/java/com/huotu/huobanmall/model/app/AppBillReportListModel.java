package com.huotu.huobanmall.model.app;

/**
 * 订单统计列表数据
 * Created by lgh on 2015/8/24.
 */
public class AppBillReportListModel {

    /**
     * 时间
     */
    private Integer time;

    /**
     * 数量
     */
    private Integer amount;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
