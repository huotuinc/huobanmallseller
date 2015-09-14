package com.huotu.huobanmall.entity;


import com.huotu.huobanmall.entity.pk.CountDayPartnerPK;

import javax.persistence.*;
import java.util.Date;

/**
 * 日分销商统计
 * 统计单位：天
 * Created by lgh on 2015/9/9.
 */
@Entity
@IdClass(CountDayPartnerPK.class)
@Cacheable(value = false)
public class CountDayPartner {

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
     * 分销商数量
     */
    private Integer amount;

    public CountDayPartner(Integer merchantId, Date date, Integer amount) {
        this.merchantId = merchantId;
        this.date = date;
        this.amount = amount;
    }

    public CountDayPartner() {
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
