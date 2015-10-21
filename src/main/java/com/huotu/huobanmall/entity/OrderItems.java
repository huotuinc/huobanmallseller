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
 * 订单商品
 * 说明：对应表Mall_Order_Items 实体OrderItemsModel
 * Created by lgh on 2015/9/9.
 */

@Entity
@Cacheable(value = false)
@Table(name = "Mall_Order_Items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Item_Id")
    private Integer id;

    /**
     * 所属订单
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Order_Id")
    private Order order;

    /**
     * 所属商品
     */
    @Column(name = "Goods_Id")
    private Integer goodsId;

    /**
     * 货品Id
     */
    @Column(name = "Product_Id")
    private Integer productId;

    /**
     * 数量
     */
    @Column(name = "Nums")
    private Integer amount;

    /**
     * 价格
     */
    @Column(name = "Price")
    private Float price;

    /**
     * 所属商户
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "Customer_Id")
    private Merchant merchant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
