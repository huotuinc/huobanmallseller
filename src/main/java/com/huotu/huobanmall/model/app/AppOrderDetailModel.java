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
    private Integer score;

    /**
     * 商品数量
     */
    private Integer amount;


    /**
     * 实付金额
     */
    private float paid;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
}
