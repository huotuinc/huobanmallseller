package com.huotu.huobanmall.entity;

import com.huotu.huobanmall.entity.pk.CountTodaySalesPK;

import javax.persistence.*;

/**
 * 今日销售额统计
 * 统计单位：小时
 * Created by lgh on 2015/9/9.
 */
@Entity
@IdClass(CountTodaySalesPK.class)
@Cacheable(value = false)
public class CountTodaySales {
    /**
     * 商家
     */
    @Id
    private Integer merchantId;

    /**
     * 小时 如 1,2,3,4
     */
    @Id
    private Integer hour;

    /**
     * 销售额
     */
    private float money;




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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
