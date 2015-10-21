/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

import com.huotu.huobanmall.entity.Device;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.Shop;

/**
 * Created by lgh on 2015/8/25.
 */
public class AppPublicModel {

    /**
     * 服务端参数
     * <p>当前用户，如果没有则为空</p>
     */
    private Merchant currentUser;

    /**
     * 当前操作员 如果是商家管理员，此值为null
     */
    private Operator currentOprator;

    /**
     * 当前店铺
     */
    private Shop currentShop;
    /**
     * 服务端参数
     * <p>当前用户设备，必然非空</p>
     */

    private Device currentDevice;
    /**
     * 平台1、HB2015AD   （android）    2、HB2015AP   （ios)
     */
    private String operation;
    /**
     * 版本号
     */
    private String version;
    /**
     * 设备号 设备必须确保唯一 也必须确保每次发送给服务端都是一致的
     */
    private String imei;
    /**
     * 验证签名
     */
    private String sign;
    /**
     * b73ca64567fb49ee963477263283a1bf
     */
    private String appKey;

    /**
     * 百度cityCode
     */
    private String cityCode;
    /**
     * 毫秒--utc
     */
    private long timestamp;
    /**
     * 身份验证  服务端负责生成 负责审核
     */
    private String token;
    /**
     * 渠道编号 默认default
     */
    private String cpaCode;
    /**
     * ip
     */
    private String ip;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCpaCode() {
        return cpaCode;
    }

    public void setCpaCode(String cpaCode) {
        this.cpaCode = cpaCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Merchant getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Merchant currentUser) {
        this.currentUser = currentUser;
    }

    public Operator getCurrentOprator() {
        return currentOprator;
    }

    public void setCurrentOprator(Operator currentOprator) {
        this.currentOprator = currentOprator;
    }

    public Shop getCurrentShop() {
        return currentShop;
    }

    public void setCurrentShop(Shop currentShop) {
        this.currentShop = currentShop;
    }

    public Device getCurrentDevice() {
        return currentDevice;
    }

    public void setCurrentDevice(Device currentDevice) {
        this.currentDevice = currentDevice;
    }
}
