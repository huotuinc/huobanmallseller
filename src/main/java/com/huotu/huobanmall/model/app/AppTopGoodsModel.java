package com.huotu.huobanmall.model.app;

/**
 * Created by lgh on 2015/9/22.
 */
public class AppTopGoodsModel {

    /**
     * 货品标题
     */
    private String title;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 价格
     */
    private float price;

    /**
     * 图片
     */
    private String picture;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
