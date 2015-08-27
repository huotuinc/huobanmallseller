package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 销售额统计报表
 * Created by lgh on 2015/8/24.
 */
public class AppSalesReportModel {
    /**
     * 本周总销售额
     */
    private Integer weekAmount;
    /**
     *  本月总销售额
     */
    private Integer monthAmount;

    /**
     * 本周或本月销售额统计数据
     */
    private List<AppSalesReportListModel> list;

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

    public List<AppSalesReportListModel> getList() {
        return list;
    }

    public void setList(List<AppSalesReportListModel> list) {
        this.list = list;
    }
}