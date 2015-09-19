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
    private Integer status;


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
}
