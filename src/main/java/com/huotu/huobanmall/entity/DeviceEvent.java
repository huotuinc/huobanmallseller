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
 * 设备事件
 */
@Entity
@Cacheable(value = false)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DeviceEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Device device;

    @Column(length = 8)
    private String versionNo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(length = 15)
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionno) {
        this.versionNo = versionno == null ? null : versionno.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createtime) {
        this.createTime = createtime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}