package com.huotu.huobanmall.entity;

import com.huotu.huobanmall.entity.pk.CountTodayOrderPK;

import javax.persistence.*;

/**
 * 今日订单统计
 * 统计单位:小时
 * Created by lgh on 2015/9/9.
 */
@Entity
@IdClass(CountTodayOrderPK.class)
@Cacheable(value = false)
public class CountTodayOrder {

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
     * 订单量
     */
    private Integer amount;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
