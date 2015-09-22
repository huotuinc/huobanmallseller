package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * 订单销售明细数据
 * Created by lgh on 2015/9/11.
 */
public class AppSalesListModel {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 付款金额
     */
    private Float money;

    /**
     * 下单时间
     */
    private Date time;

    /**
     * 订单图片
     */
    private String pictureUrl;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
