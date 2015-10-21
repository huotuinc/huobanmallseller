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
 * 返利积分正式表
 * 说明：对应表 Hot_UserIntegral_History 实体UserTempIntegralHistoryModel
 * Created by lgh on 2015/9/7.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Hot_UserIntegral_History")
public class RebateDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UIH_ID")
    private Integer id;

    /**
     * 所属订单
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "UIH_Order_Id")
    private Order order;

    /**
     * 商家
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "UIH_CustomerID")
    private Merchant merchant;

    /**
     * 获得积分的用户
     */
    @Column(name = "UIH_UserID")
    private Integer userId;

    /**
     * 返利积分
     */
    @Column(name = "UIH_Integral")
    private Integer score;


    /**
     * 状态(1.购买商品获得)
     */
    @Column(name = "UIH_Type")
    private Integer status;

    /**
     * 生成时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UIH_AddTime")
    private Date time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
