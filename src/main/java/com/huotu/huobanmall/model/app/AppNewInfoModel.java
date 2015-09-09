package com.huotu.huobanmall.model.app;

/**
 * app首页 （统计数据）
 * Created by lgh on 2015/8/24.
 */
public class AppNewInfoModel {
    /**
     * 总销售额
     */
    private float totalSalesAmount;
    /**
     * 今日销售总额
     */
    private float todaySalesAmount;

    /**
     * 今日新增订单数
     */
    private Integer todayBillAmount;
    /**
     * 今日新增分销商数量
     */
    private Integer todayDiscributorAmount;

    /**
     * 今日新增会员数
     */
    private Integer todayNewUserAmount;

    /**
     * 今日新增订单详情
     */
    private Integer[] todayBillDetails;

    public float getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(float totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public Integer getTodayBillAmount() {
        return todayBillAmount;
    }

    public void setTodayBillAmount(Integer todayBillAmount) {
        this.todayBillAmount = todayBillAmount;
    }

    public float getTodaySalesAmount() {
        return todaySalesAmount;
    }

    public void setTodaySalesAmount(float todaySalesAmount) {
        this.todaySalesAmount = todaySalesAmount;
    }

    public Integer getTodayDiscributorAmount() {
        return todayDiscributorAmount;
    }

    public void setTodayDiscributorAmount(Integer todayDiscributorAmount) {
        this.todayDiscributorAmount = todayDiscributorAmount;
    }
    public Integer getTodayNewUserAmount() {
        return todayNewUserAmount;
    }

    public void setTodayNewUserAmount(Integer todayNewUserAmount) {
        this.todayNewUserAmount = todayNewUserAmount;
    }
}
