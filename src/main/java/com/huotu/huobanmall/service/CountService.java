package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;

import java.util.Date;
import java.util.Map;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface CountService {


    /**
     * 获取当天订单数据
     * @param merchant
     * @return
     */
    Map<Integer,Integer> todayOrder(Merchant merchant);

    /**
     * 获取当天会员数据
     * @param merchant
     * @return
     */
    Map<Integer,Integer> todayMember(Merchant merchant);

    /**
     * 获取当天分销商的数据(小伙伴)
     * @param merchant
     * @return
     */
    Map<Integer,Integer> todayPartner(Merchant merchant);
    /**
     * 获取本周订单量
     *
     * @param merchant
     * @return
     */
    Map<Date, Integer> getWeekOrder(Merchant merchant);

    /**
     * 获取本月订单量
     *
     * @param merchant
     * @return
     */
    Map<Date, Integer> getMonthOrder(Merchant merchant);


    /**
     * 本周会员量
     *
     * @param merchant
     * @return
     */
    Map<Date, Integer> getWeekMember(Merchant merchant);


    /**
     * 本月会员量
     *
     * @param merchant
     * @return
     */
    Map<Date, Integer> getMonthMember(Merchant merchant);

    /**
     * 本周小伙伴量
     *
     * @param merchant
     * @return
     */
    Map<Date, Integer> getWeekPartner(Merchant merchant);


    /**
     * 本月小伙伴量
     *
     * @param merchant
     * @return
     */
    Map<Date, Integer> getMonthPartner(Merchant merchant);

    /**
     * 今日销售额
     *
     * @param merchant
     * @return
     */
    Map<Integer, Float> getDaySales(Merchant merchant);


    /**
     * 本周销售额
     *
     * @param merchant
     * @return
     */
    Map<Date, Float> getWeekSales(Merchant merchant);

    /**
     * 本月销售额
     *
     * @param merchant
     * @return
     */
    Map<Date, Float> getMonthSales(Merchant merchant);

    /**
     * 计算总销售额(不含今日)
     * @param merchant
     * @return
     */
    Float getTotalSales(Merchant merchant);

    /**
     * 计算总订单量(不含今日)
     * @param merchant
     * @return
     */
    Long getTotalOrders(Merchant merchant);

    /**
     * 计算总会员数量(不含今日)
     * @param merchant
     * @return
     */
    Long getTotalMembers(Merchant merchant);

    /**
     * 计算总小伙伴数量(不含今日)
     * @param merchant
     * @return
     */
    Long getTotalPartner(Merchant merchant);


}
