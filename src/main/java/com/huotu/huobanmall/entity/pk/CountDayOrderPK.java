package com.huotu.huobanmall.entity.pk;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lgh on 2015/9/10.
 */
public class CountDayOrderPK implements Serializable {

    private Integer merchantId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
