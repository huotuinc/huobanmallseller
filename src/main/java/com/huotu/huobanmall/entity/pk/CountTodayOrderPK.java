package com.huotu.huobanmall.entity.pk;

import com.huotu.huobanmall.entity.Merchant;

import java.io.Serializable;

/**
 * Created by lgh on 2015/9/10.
 */
public class CountTodayOrderPK implements Serializable {
    private Merchant merchant;
    private Integer hour;

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
}
