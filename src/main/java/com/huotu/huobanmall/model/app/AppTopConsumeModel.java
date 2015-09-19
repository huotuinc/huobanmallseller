package com.huotu.huobanmall.model.app;

/**
 * Created by lgh on 2015/9/11.
 */
public class AppTopConsumeModel {

    /**
     * 用户头像地址
     */
    private String pictureUrl;

    /**
     * 用户姓名
     */
    private String name;


    /**
     * 手机号
     */
    private String mobile;

    /**
     * 消费额
     */
    private float money;

    /**
     * 购买单数
     */
    private  Integer amount;

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



    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
