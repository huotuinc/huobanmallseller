package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 销售日志
 * Created by lgh on 2015/9/21.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Sell_Logs")
public class SellLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_Id")
    private Integer id;

    /**
     * 商户
     */
    @Column(name = "Customer_Id")
    private Integer merchantId;

    /**
     * 用户Id
     */
    @Column(name = "Member_Id")
    private Integer userId;

    /**
     * 会员名
     */
    @Column(name = "Name")
    private String name;


    /**
     * 价格
     */
    @Column(name = "Price")
    private String price;


    /**
     * 产品Id
     */
    @Column(name = "Product_Id")
    private Integer productId;

    /**
     * 商品Id
     */
    @Column(name = "Goods_Id")
    private Integer goodsId;

    /**
     * 商品名称
     */
    @Column(name = "Product_Name")
    private String productName;

    /**
     * 商品描述
     */
    @Column(name = "Pdt_Desc")
    private String desc;

    /**
     * 数量
     */
    @Column(name = "Number")
    private Integer amount;


    /**
     * 时间
     */
    @Column(name = "Createtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
