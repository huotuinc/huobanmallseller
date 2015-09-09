package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 今日销售额统计
 * 统计单位：小时
 * Created by lgh on 2015/9/9.
 */
@Entity
@Cacheable(value = false)
public class CountTodaySales {
    /**
     * 商家
     */
    @Id
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Merchant merchant;

    /**
     * 小时 如 1,2,3,4
     */
    @Id
    private Integer hour;

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

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
