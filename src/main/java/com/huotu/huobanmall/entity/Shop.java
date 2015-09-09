package com.huotu.huobanmall.entity;

import javax.persistence.*;

/**
 * 店铺 todo new 店铺会不会不存在 跟商家不匹配
 * 备注：对应表Mall_UserBaseConfig  实体MallUserBaseConfigModel
 * Created by lgh on 2015/9/6.
 */

@Entity
@Cacheable(value = false)
@Table(name = "Mall_UserBaseConfig")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfigID")
    private Integer id;

    /**
     * 所属商家 todo 不能一一对应需要处理
     */
    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "CustomerID")
    private Merchant merchant;

    /**
     * 店铺名称
     */
    @Column(name = "MallName")
    private String title;
    /**
     * 店铺描述
     */
    @Column(name = "SaleTag")
    private String discription;

    /**
     * 店铺logo
     */
    @Column(name = "Logo")
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
