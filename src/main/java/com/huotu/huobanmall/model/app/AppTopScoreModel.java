/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

/**
 * 返利积分数据
 * Created by lgh on 2015/9/11.
 */
public class AppTopScoreModel {

    /**
     * 用户头像地址
     */
    private String pictureUrl;

    /**
     * 用户姓名
     */
    private String name;

//    /**
//     * 用户昵称
//     */
//    private String nickName;
//
//    /**
//     * 手机号
//     */
//    private  String mobile;


    /**
     * 返利积分
     */
    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
