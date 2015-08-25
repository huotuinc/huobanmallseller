package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 订单统计数据
 * Created by Administrator on 2015/8/24.
 */
public class AppBillReportModel {

    /**
     * 本周总订单量
     */
    private Integer weekAmount;
    /**
     *  本月总订单量
     */
    private Integer monthAmount;

    /**
     * 本周或本月的具体订单量数据
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
