/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 货品 Mall_Products ProductsModel
 * Created by lgh on 2015/9/9.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id")
    private Integer id;

    /**
     * 所属商品
     */
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "Goods_Id")
    private Goods goods;


    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "Customer_Id")
    private Merchant merchant;

    /**
     * 规格说明
     */
    @Column(name = "Pdt_Desc")
    private String spec;

    /**
     * 名称
     */
    @Column(name = "Name")
    private String name;

    /**
     * 价格
     */
    @Column(name = "Price")
    private Float price;

    /**
     * 上架状态
     */
    @Column(name = "Marketable")
    private Integer marketStatus;


    @Column(name = "Is_Local_Stock")
    private Integer isLocalStock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(Integer marketStatus) {
        this.marketStatus = marketStatus;
    }

    public Integer getIsLocalStock() {
        return isLocalStock;
    }

    public void setIsLocalStock(Integer isLocalStock) {
        this.isLocalStock = isLocalStock;
    }
}
