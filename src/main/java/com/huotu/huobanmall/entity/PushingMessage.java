/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.entity;

import com.huotu.common.model.AppOS;
import com.huotu.huobanmall.config.CommonEnum;


import javax.persistence.*;
import java.util.Set;

/**
 * 一条待推送的消息，推送完成以后将被删除
 * <p>每一条消息将被发送时 需要判断是否多渠道(ios or Android)</p>
 *
 * @author CJ
 */
@Entity
@Cacheable(value = false)
public class PushingMessage implements Cloneable {

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public PushingMessage clone() {
        PushingMessage message = new PushingMessage();
        message.setTitle(this.title);
        message.setType(this.type);
        message.setOs(this.os);
        message.setData(this.data);
        message.setDeviceTokens(this.deviceTokens);
        message.setId(this.id);
        message.setUsername(this.username);
        return message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private CommonEnum.PushMessageType type;
    /**
     * 消息的附件数据
     */
    private String data;
    /**
     * 消息的标题
     */
    private String title;
    @Column(nullable = false)
    private AppOS os;

    /**
     * 接受消息的用户名(只用于app端判断)
     */
    @Column(length = 20)
    private String username;

    @ElementCollection
    private Set<String> deviceTokens;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppOS getOs() {
        return os;
    }

    public void setOs(AppOS os) {
        this.os = os;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CommonEnum.PushMessageType getType() {
        return type;
    }

    public void setType(CommonEnum.PushMessageType type) {
        this.type = type;
    }

    public Set<String> getDeviceTokens() {
        return deviceTokens;
    }

    public void setDeviceTokens(Set<String> deviceTokens) {
        this.deviceTokens = deviceTokens;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
