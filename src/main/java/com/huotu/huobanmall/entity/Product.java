package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 商品
 * Created by lgh on 2015/8/26.
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品标题
     */
    @Column
    private String title;


    /**
     * 商家Id
     */
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private Merchant owner;
    /**
     * 图片地址
     */
    @Column
    private String pictureUrl;

    /**
     * 库存量 -1无限制
     */
    @Column
    private Integer stock;

    /**
     * 商品状态 1 上架 2 下架 3 删除 todo 根据实际情况需要修改
     */
    @Column
    private Integer status;

    /**
     * 商品价格
     */
    @Column
    private float price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public Merchant getOwner() {
        return owner;
    }

    public void setOwner(Merchant owner) {
        this.owner = owner;
    }
}
