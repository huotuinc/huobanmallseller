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

/**
 * 操作员
 * 说明：对应表Mall_Manager 实体 MallManagerModel
 * Created by lgh on 2015/8/31.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Manager")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ManagerID")
    private Integer id;
    /**
     * 用户名
     */
    @Column(name = "LoginName")
    private String name;
    /**
     * 密码
     */
    @Column(name = "LoginPassword")
    private String password;

    /**
     * 所属商户
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "CustomerID")
    private Merchant merchant;

//    /**
//     * 昵称 显示在app中
//     */
//    @Column(length = 50)
//    private String nickName;

    /**
     * 权限，控制app端的内容显示 以,隔开 如 11,33,55
     * todo new
     */
    @Column(length = 2000, name = "AppMenuAuthorize")
    private String authority;


    /**
     * 订单支付成功通知（0关闭,1开启）
     * todo new
     */
    private boolean enableBillNotice;
    /**
     * 新增小伙伴通知（0关闭，1开启）
     * todo new
     */
    private boolean enablePartnerNotice;

    /**
     * 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     * todo new
     */
    private boolean noDisturbed;

    /**
     * 是否可用
     * todo new
     */
//    private boolean isEnabled;

    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递
     * <b>每次App获得新的Token,旧Token就弃用。</b>
     * todo new
     */
    @Column(length = 32,name = "AppToken")
    private String token;


    /**
     * 当前所用设备
     * todo new
     */
    @ManyToOne
    @JoinColumn(name = "Device")
    private Device device;

    /**
     * 激活状态 0为激活，1为冻结
     */
    @Column(name = "State")
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean isEnableBillNotice() {
        return enableBillNotice;
    }

    public void setEnableBillNotice(boolean enableBillNotice) {
        this.enableBillNotice = enableBillNotice;
    }

    public boolean isEnablePartnerNotice() {
        return enablePartnerNotice;
    }

    public void setEnablePartnerNotice(boolean enablePartnerNotice) {
        this.enablePartnerNotice = enablePartnerNotice;
    }

    public boolean isNoDisturbed() {
        return noDisturbed;
    }

    public void setNoDisturbed(boolean noDisturbed) {
        this.noDisturbed = noDisturbed;
    }

//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setIsEnabled(boolean isEnabled) {
//        this.isEnabled = isEnabled;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
