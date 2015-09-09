package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 日销售额统计
 * 统计单位：天
 * Created by lgh on 2015/9/9.
 */
@Entity
@Cacheable(value = false)
public class CountDaySales {
    /**
     * 商家
     */
    @Id
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Merchant merchant;

    /**
     * 日期
     */
    @Id
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 销售额
     */
    private float money;


    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
