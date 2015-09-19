package com.huotu.huobanmall.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 系统配置 todo 对应数据库
 */
@Entity
@Cacheable(value = false)
@Deprecated
public class SystemConfig {
    @Id
    @Column(length = 50)
    private String code;

    @Column(length = 100)
    private String remark;

    @Column(nullable = false,length = 100)
    private String valueForCode;

    public SystemConfig(String code, String remark, String valueForCode) {
        this.code = code;
        this.remark = remark;
        this.valueForCode = valueForCode;
    }

    public SystemConfig() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getValueForCode() {
        return valueForCode;
    }

    public void setValueForCode(String value) {
        this.valueForCode = value == null ? null : value.trim();
    }
}