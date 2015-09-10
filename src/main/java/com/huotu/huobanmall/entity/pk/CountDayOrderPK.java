package com.huotu.huobanmall.entity.pk;

import com.huotu.huobanmall.entity.Merchant;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lgh on 2015/9/10.
 */
public class CountDayOrderPK implements Serializable {

    private Merchant merchant;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

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
}
