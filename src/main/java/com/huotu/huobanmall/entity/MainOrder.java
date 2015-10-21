/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 主订单
 * Created by lgh on 2015/9/25.
 */
@Entity
@Table(name = "Mall_UnionOrder")
@Cacheable(value = false)
public class MainOrder {

    @Id
    @Column(name = "Union_Order_Id")
    private  String id;


    /**
     * 商家
     */
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "Customer_Id")
    private  Merchant merchant;

    /**
     * 用户
     */
    @Column(name ="Member_Id" )
    private Integer userId;

    /**
     * 订单状态 0活动 -1死单 1已完成
     */
    @Column(name = "Status")
    private Integer status;

    /**
     * 付款状态  0：未支付|1：已支付|2：已支付至担保方|3：部分付款|4：部分退款|5：全额退款
     */
    @Column(name = "Pay_Status")
    private Integer payStatus;


    /**
     * 发货状态
     * 0：未发货|1：已发货|2：部分发货|3：部分退货|4：已退货
     */
    @Column(name = "Ship_Status")
    private Integer deliverStatus;

    /**
     *时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Createtime")
    private Date  time;

    /**
     * 接收状态  1为签收
     */
    @Column(name = "Rel_receiveStatus")
    private Integer receivestatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(Integer deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public Integer getReceivestatus() {
        return receivestatus;
    }

    public void setReceivestatus(Integer receivestatus) {
        this.receivestatus = receivestatus;
    }
}
