package com.huotu.huobanmall.model.app;

/**
 * app首页 （统计数据）
 * Created by lgh on 2015/8/24.
 */
public class AppIndexModel {
    /**
     * 商品数量
     */
    private Integer goodsAmount;

    /**
     * 分销商数量
     */
    private Integer discributorAmount;

    /**
     * 会员数量
     */
    private Integer memberAmount;

    /**
     * 总销售额
     */
    private float totalSalesAmount;

    /**
     * 今日订单数
     */
    private Integer todayBillAmount;

    /**
     * 今日销售总额
     */
    private float todaySalesAmount;

    /**
     * 今日分销商数量
     */
    private Integer todayDiscributorAmount;

    /**
     * 近七日订单量
     */
    private Integer sevenBillAmount;

    /**
     * 近七日销售额
     */
    private float sevenSalesAmount;

    /**
     * 今日新增会员数
     */
    private Integer todayNewUserAmount;

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getDiscributorAmount() {
        return discributorAmount;
    }

    public void setDiscributorAmount(Integer discributorAmount) {
        this.discributorAmount = discributorAmount;
    }

    public Integer getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(Integer memberAmount) {
        this.memberAmount = memberAmount;
    }

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

    public Integer getSevenBillAmount() {
        return sevenBillAmount;
    }

    public void setSevenBillAmount(Integer sevenBillAmount) {
        this.sevenBillAmount = sevenBillAmount;
    }

    public float getSevenSalesAmount() {
        return sevenSalesAmount;
    }

    public void setSevenSalesAmount(float sevenSalesAmount) {
        this.sevenSalesAmount = sevenSalesAmount;
    }

    public Integer getTodayNewUserAmount() {
        return todayNewUserAmount;
    }

    public void setTodayNewUserAmount(Integer todayNewUserAmount) {
        this.todayNewUserAmount = todayNewUserAmount;
    }
}
