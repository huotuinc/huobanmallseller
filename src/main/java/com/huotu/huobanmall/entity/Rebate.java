package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 返利积分表
 * 说明：对应表 Hot_UserTempIntegral_History 实体UserTempIntegralHistoryModel
 * Created by lgh on 2015/9/7.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Hot_UserTempIntegral_History")
public class Rebate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UTIH_ID")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "UTIH_Order_Id")
    private Order order;

    /**
     * 商家
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "UTIH_CustomerID")
    private Merchant merchant;

    /**
     * 用户
     */
    @Column(name = "UTIH_UserID")
    private Integer userId;

    /**
     * 返利积分
     */
    @Column(name = "UTIH_Integral")
    private Integer score;


    /**
     * 状态(1:已转正，0:待转正：-1:待转正状态下被作废，-2：已转正状态下被作废)
     */
    @Column(name = "UTIH_Status")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UTIH_AddTime")
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
