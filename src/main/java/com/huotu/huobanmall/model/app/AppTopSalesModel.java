package com.huotu.huobanmall.model.app;

/**
 * Created by lgh on 2015/9/11.
 */
public class AppTopSalesModel {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 价格
     */
    private float price;

    /**
     * 图片地址
     */
    private String pictureUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
