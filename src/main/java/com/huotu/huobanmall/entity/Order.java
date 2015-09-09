package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单
 * 说明：对应表 Mall_Orders 实体 OrdersModel
 * Created by lgh on 2015/8/26.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Orders")
public class Order {
    /**
     *  订单号(yyyyMMdd+8位随机数)  todo 订单号格式
     */
    @Id
    @Column(name = "Order_Id")
    private String id;

    /**
     * 所属商家
     */
    @ManyToOne
    @JoinColumn(name = "Customer_Id")
    private Merchant merchant;

//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private User owner;
//
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private Goods product;


    /**
     * 用户 todo new
     */
    @Column(name = "Member_Id")
    private Integer userId;

    /**
     * 用户类型 0普通会员1小伙伴
     */
    @Column(name = "Rel_UserType")
    private Integer userType;

    /**
     * 订单名称
     */
    @Column(name = "Tostr")
    private String title;

//    /**
//     * 图片 需要从商品表获取
//     */
//    private String pictureUrl;

    /**
     * 订单状态 0：未支付|1：已支付|2：已支付至担保方|3：部分付款|4：部分退款|5：全额退款
     */
    @Column(name = "Pay_Status")
    private Integer orderStatus;


    /**
     * 收货人
     */
    @Column(name = "Ship_Name")
    private String receiver;

//    /**
//     * 返利积分
//     */
//    private Integer score;


    /**
     * 商品数量
     */
    @Column(name = "Itemnum")
    private Integer amount;
    /**
     * 订单总金额
     */
    @Column(name = "Final_Amount")
    private float price;


    /**
     * 下单时间 按照此排序
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "Createtime")
    private Date time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
