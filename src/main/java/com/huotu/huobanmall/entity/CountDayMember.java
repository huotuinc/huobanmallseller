/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.entity;

import com.huotu.huobanmall.entity.pk.CountDayMemberPK;

import javax.persistence.*;
import java.util.Date;

/**
 * 日会员统计
 * 统计单位：天
 * Created by lgh on 2015/9/9.
 */
@Entity
@IdClass(CountDayMemberPK.class)
@Cacheable(value = false)
public class CountDayMember {

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
     * 会员量
     */
    private Integer amount;

    public CountDayMember(Integer merchantId, Date date, Integer amount) {
        this.merchantId = merchantId;
        this.date = date;
        this.amount = amount;
    }

    public CountDayMember() {
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
