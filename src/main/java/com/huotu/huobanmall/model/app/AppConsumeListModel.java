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
 * Created by lgh on 2015/9/22.
 */

public class AppConsumeListModel {
    /**
     * 用户头像地址
     */
    private String pictureUrl;

    /**
     * 用户姓名
     */
    private String name;


//    /**
//     * 手机号
//     */
//    private String mobile;

    /**
     * 消费额
     */
    private double money;


    /**
     * 时间
     */
    private  Date time;

    /**
     * 数量
     */
    private Integer amount;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
