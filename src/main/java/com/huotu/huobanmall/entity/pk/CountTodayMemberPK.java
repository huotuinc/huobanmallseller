package com.huotu.huobanmall.entity.pk;

import com.huotu.huobanmall.entity.Merchant;

import java.io.Serializable;

/**
 * Created by lgh on 2015/9/10.
 */
public class CountTodayMemberPK implements Serializable {

    private Integer merchantId;

    private Integer hour;

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
}
