package com.huotu.huobanmall.model.app;

import java.util.Date;

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
    private Integer status;

    /**
     * 图片地址
     */
    private String pictureUrl;

    /**
     * 订单标题
     */
    private String title;

    /**
     * 收货人
     */
    private  String receiver;

    /**
     * 下单时间
     */
    private Date time;
    /**
     * 商品数量
     */
    private Integer amount;

    /**
     * 付款金额
     */
    private Float money;

    /**
     * 返利积分
     */
    private Integer score;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
