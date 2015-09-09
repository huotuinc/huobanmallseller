package com.huotu.huobanmall.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * 日分销商统计
 * 统计单位：天
 * Created by lgh on 2015/9/9.
 */
@Entity
@Cacheable(value = false)
public class CountDayPartner {

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
     * 分销商数量
     */
    private Integer amount;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
