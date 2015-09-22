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
    private float money;


    /**
     * 时间
     */
    private  Date time;

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

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
