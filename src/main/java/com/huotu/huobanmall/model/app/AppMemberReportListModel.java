package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * 会员量统计数据
 * Created by lgh on 2015/8/24.
 */
public class AppMemberReportListModel {

    /**
     * 时间
     */
    private Date time;
    /**
     * 数量
     */
    private Integer amount;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
