package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * 销售明细数据
 * Created by lgh on 2015/9/11.
 */
public class AppSalesListModel {

    /**
     * 订单号
     */
    private String orderNo;



    /**
     * 订单标题
     */
    private String title;

    /**
     * 购买人姓名
     */
    private  String receiver;

    /**
     * 手机号
     */
    private  String moblie;


    /**
     * 付款金额
     */
    private Float money;

    /**
     * 下单时间
     */
    private Date time;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
