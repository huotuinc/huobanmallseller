package com.huotu.huobanmall.entity;

import com.huotu.huobanmall.entity.pk.CountDaySalesPK;

import javax.persistence.*;
import java.util.Date;

/**
 * 日销售额统计
 * 统计单位：天
 * Created by lgh on 2015/9/9.
 */
@Entity
@IdClass(CountDaySalesPK.class)
@Cacheable(value = false)
public class CountDaySales {
    /**
     * 商家
     */
    @Id
    private Integer merchantId;

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


    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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
