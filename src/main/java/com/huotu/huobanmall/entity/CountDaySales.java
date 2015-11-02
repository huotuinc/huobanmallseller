/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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
    @Column(columnDefinition="decimal(18,2)")
    private float money;

    public CountDaySales(Integer merchantId, Date date, float money) {
        this.merchantId = merchantId;
        this.date = date;
        this.money = money;
    }

    public CountDaySales() {
    }


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
