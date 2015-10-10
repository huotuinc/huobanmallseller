package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 商品
 * 一个商品包含多个货品 product
 * 说明：对应表Mall_Goods 实体GoodsModel
 * Created by lgh on 2015/8/26.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Goods")
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Goods_Id")
    private Integer id;


    /**
     * 所属分类
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "Cat_Id")
    private Category category;

    /**
     * 商品标题
     */
    @Column(name = "Name")
    private String title;


    /**
     * 商家
     */
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "Customer_Id")
    private Merchant owner;
    /**
     * 图片地址
     */
    @Column(name = "Image_Default")
    private String pictureUrl;

    /**
     * 库存量 -1无限制
     */
    @Column(name = "Store")
    private Integer stock;

    /**
     * 商品状态 1 上架 2 下架 3 删除 todo 根据实际情况需要修改
     */
    @Column(name = "Marketable")
    private Integer status;

    /**
     * 商品价格
     */
    @Column(name = "Price")
    private float price;

    /**
     * 商品是否可用 0表示可用
     */
    @Column(name = "Disabled")
    private Integer disabled;


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

    public Merchant getOwner() {
        return owner;
    }

    public void setOwner(Merchant owner) {
        this.owner = owner;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }
}
