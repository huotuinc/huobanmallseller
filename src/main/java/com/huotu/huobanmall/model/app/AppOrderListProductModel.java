package com.huotu.huobanmall.model.app;

/**
 * Created by lgh on 2015/9/15.
 */
public class AppOrderListProductModel {


    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 名称
     */
    private String title;
    /**
     * 付款金额
     */
    private Float money;
    /**
     * 商品数量
     */
    private Integer amount;

    /**
     * 规格说明
     */
    private String spec;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
