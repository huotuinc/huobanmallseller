/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

import java.util.Date;
import java.util.List;

/**
 * 订单数据 （订单管理）
 * Created by lgh on 2015/9/11.
 */
public class AppOrderListModel {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private String status;


    /**
     * 规格数据
     */
    private List<AppOrderListProductModel> list;


//    /**
//     * 返利积分
//     */
//    private Integer score;

    /**
     * 商品数量
     */
    private Integer amount;


    /**
     * 实付金额
     */
    private float paid;


    /**
     * 下单时间
     */
    private Date time;

    /**
     * 主订单号
     */
    private String mainOrderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AppOrderListProductModel> getList() {
        return list;
    }

    public void setList(List<AppOrderListProductModel> list) {
        this.list = list;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }
}
