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
 * 订单管理详情
 * Created by lgh on 2015/9/15.
 */
public class AppOrderDetailModel {

    /**
     * 购买人
     */
    private String buyer;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 规格数据
     */
    private List<AppOrderListProductModel> list;

    /**
     * 返利积分
     */
    private List<AppUserRebateModel> scoreList;

    /**
     * 商品数量
     */
    private Integer amount;


    /**
     * 实付金额
     */
    private double paid;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public List<AppOrderListProductModel> getList() {
        return list;
    }

    public void setList(List<AppOrderListProductModel> list) {
        this.list = list;
    }

    public List<AppUserRebateModel> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<AppUserRebateModel> scoreList) {
        this.scoreList = scoreList;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
