package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppBillReportListModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface CountService {


    List<AppBillReportListModel> todayOrder(Merchant merchant);

    List<AppBillReportListModel> weekOrder(Merchant merchant);


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


}
