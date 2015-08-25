package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 订单统计数据
 * Created by Administrator on 2015/8/24.
 */
public class AppBillReportModel {

    /**
     * 本周
     */
    private Integer weekAmount;
    /**
     *  本月
     */
    private Integer monthAmount;

    /**
     * 本周或本月的数据
     */
    private List<AppBillReportListModel> list;

    public Integer getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Integer weekAmount) {
        this.weekAmount = weekAmount;
    }

    public Integer getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Integer monthAmount) {
        this.monthAmount = monthAmount;
    }

    public List<AppBillReportListModel> getList() {
        return list;
    }

    public void setList(List<AppBillReportListModel> list) {
        this.list = list;
    }
}
