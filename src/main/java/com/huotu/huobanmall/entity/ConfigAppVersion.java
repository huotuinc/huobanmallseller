package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * APP版本配置
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