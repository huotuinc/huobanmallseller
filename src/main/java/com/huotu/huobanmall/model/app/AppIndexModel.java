package com.huotu.huobanmall.model.app;

/**
 * app首页 （统计数据）
 * Created by Administrator on 2015/8/24.
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

}
