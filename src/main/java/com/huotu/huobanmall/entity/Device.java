/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 抽象具体的设备
 * @author CJ
 */
@Entity
@Cacheable(value = false)
public class Device {

    /**
     * 唯一识别的imei，即使是不同种类的平台也需要保证生成的imei是完全不同的
     */
    @Id
    @Column(length = 50)
    private String imei;
    /**
     * 用于推送的token
     */
    @Column(length = 64)
    private String pushingToken;
    /**
     * 进入本系统的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemDate;
    /**
     * 与之相关的渠道信息
     */
    @ManyToOne
    private CPA cpa;
    @ManyToOne
    private ConfigArea area;

    public CPA getCpa() {
        return cpa;
    }

    public void setCpa(CPA cpa) {
        this.cpa = cpa;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPushingToken() {
        return pushingToken;
    }

    public void setPushingToken(String pushingToken) {
        this.pushingToken = pushingToken;
    }

    public Date getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(Date systemDate) {
        this.systemDate = systemDate;
    }

    public ConfigArea getArea() {
        return area;
    }

    public void setArea(ConfigArea area) {
        this.area = area;
    }
}
