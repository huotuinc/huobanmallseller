package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 运单（发货退货）
 * 对应Mall_Delivery 实体DeliveryModel
 * Created by lgh on 2015/9/18.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Mall_Delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Delivery_Id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Order_Id")
    private Order order;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Member_Id")
    private User user;

    /**
     * 物流公司名称
     */
    @Column(name = "Logi_Name")
    private String deliveryName;


    /**
     * 物流单号
     */
    @Column(name = "Logi_No")
    private String no;

    /**
     * 收/退货人名字
     */
    @Column(name = "Ship_Name")
    private String reciever;

//    /**
//     * 行政区域
//     */
//    @Column(name = "Ship_Area")
//    private String area;
    /**
     * 地址
     */
    @Column(name = "Ship_Addr")
    private String address;

    /**
     * 邮编
     */
    @Column(name = "Ship_Zip")
    private String zip;

    /**
     * 电话
     */
    @Column(name = "Ship_Tel")
    private String telephone;

    /**
     * 手机号
     */
    @Column(name = "Ship_Mobile")
    private String mobile;

    /**
     * 物流费用
     */
    @Column(name = "Money")
    private Float money;

    /**
     * 状态
     * <p>
     * 成功到达
     * <p>
     * succ,
     * <p>
     * 发货失败
     * <p>
     * failed,
     * <p>
     * 已取消
     * <p>
     * cancel,
     * <p>
     * 货物丢失
     * <p>
     * lost,
     * <p>
     * 运送中
     * <p>
     * progress,
     * <p>
     * 超时
     * <p>
     * timeout,
     * <p>
     * 准备发货
     * <p>
     * ready
     */
    @Column(name = "Status")
    private String status;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
