package com.huotu.huobanmall.model.app;

/**
 * 商品列表
 * Created by Administrator on 2015/8/24.
 */
public class AppGoodListModel {

    /**
     * 图片地址
     */
    private String pictureUrl;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 库存量
     */
    private Integer stock;

    /**
     * 销售价格(元)
     */
    private float price;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
