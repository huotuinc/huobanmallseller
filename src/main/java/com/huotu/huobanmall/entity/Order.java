package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单
 * Created by lgh on 2015/8/26.
 */
@Entity
public class Order {

    /**
     * 订单号
     */
    @Id
    private String id;

//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private User owner;
//
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private Product product;


    /**
     * 用户
     */
    private Integer userId;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 下单时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date time;

    /**
     * 订单状态 1 未付款 2已付款 todo 需要根据实际情况调整
     */
    private Integer orderStatus;


    /**
     * 收货人
     */
    private String receiver;

    /**
     * 返利积分
     */
    private Integer score;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}