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

    /**
     * 所属订单
     */
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
     * 获得积分的用户
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

    /**
     * 生成时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UTIH_AddTime")
    private Date time;

    /**
     * 预计转正时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UTIH_Estimate_PosTime")
    private Date scheduledTime;

    /**
     * 实际转正时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UTIH_PositiveTime")
    private Date actualTime;

    /**
     *  获取返利积分人员
     *  购买商品获得 = 1,
     *  下线购买商品提成 = 2,
     *  下下线购买商品提成 = 201,
     *  下下下线购买商品提成 = 202,
     */
    @Column(name = "UTIH_NewType")
    private Integer gainer;

    @Column(name = "UTIH_Type")
    private Integer type;

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

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public Integer getGainer() {
        return gainer;
    }

    public void setGainer(Integer gainer) {
        this.gainer = gainer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer scores) {
        this.score = scores;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
