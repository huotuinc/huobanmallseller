/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * 用户反馈实体类
 * Created by Administrator on 2015/6/8.
 */
public class AppFeedbackModel {
    private int id;
    private String turnUserId;
    private String name;
    private String contact;
    private String content;
    private Date createTime;
    /**
     * 是否处理，1表示已处理，0表示为处理
     */
    private int isDo;
    private String remark;
    private Date doTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTurnUserId() {
        return turnUserId;
    }

    public void setTurnUserId(String turnUserId) {
        this.turnUserId = turnUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsDo() {
        return isDo;
    }

    public void setIsDo(int isDo) {
        this.isDo = isDo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }
}
