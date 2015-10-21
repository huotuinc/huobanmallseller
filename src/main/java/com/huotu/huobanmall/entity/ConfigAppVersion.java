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
 * APP版本配置 todo 对应数据库
 */
@Entity
@Cacheable(value = false)
public class ConfigAppVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8)
    private String versionNo;

    @Column(length = 200)
    private String sourceFileUrl;

    @Column(length = 200)
    private String differenceFileUrl;

    private boolean bigError;

    @Column(length = 32)
    private String md5value;

    @Temporal(TemporalType.DATE)
    private Date updateTime;

    @Column(length = 500)
    private String description;

    public boolean isBigError() {
        return bigError;
    }

    public void setBigError(boolean bigError) {
        this.bigError = bigError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifferenceFileUrl() {
        return differenceFileUrl;
    }

    public void setDifferenceFileUrl(String differenceFileUrl) {
        this.differenceFileUrl = differenceFileUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMd5value() {
        return md5value;
    }

    public void setMd5value(String md5value) {
        this.md5value = md5value;
    }

    public String getSourceFileUrl() {
        return sourceFileUrl;
    }

    public void setSourceFileUrl(String sourceFileUrl) {
        this.sourceFileUrl = sourceFileUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}