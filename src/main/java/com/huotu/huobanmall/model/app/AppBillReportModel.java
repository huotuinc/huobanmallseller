/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 订单统计数据
 * Created by lgh on 2015/8/24.
 */
public class AppBillReportModel {

    /**
     * 本周总订单量
     */
    private Integer weekAmount;
    /**
     * 本月总订单量
     */
    private Integer monthAmount;

    /**
     * 本周或本月的具体订单量数据
     */
    private List<AppBillReportListModel> listBill;

    /**
     * 本周或本月商品购买量排行
     */
    private List<AppTopBuyAmountModel> listBuy;

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

    public List<AppBillReportListModel> getListBill() {
        return listBill;
    }

    public void setListBill(List<AppBillReportListModel> listBill) {
        this.listBill = listBill;
    }

    public List<AppTopBuyAmountModel> getListBuy() {
        return listBuy;
    }

    public void setListBuy(List<AppTopBuyAmountModel> listBuy) {
        this.listBuy = listBuy;
    }
}
