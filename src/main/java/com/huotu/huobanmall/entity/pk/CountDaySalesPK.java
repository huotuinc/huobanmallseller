package com.huotu.huobanmall.entity.pk;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lgh on 2015/9/10.
 */
public class CountDaySalesPK implements Serializable {

    private Integer merchantId;

    /**
     * 日期
     */

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

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
}
