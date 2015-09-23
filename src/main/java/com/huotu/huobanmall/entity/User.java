package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 * 说明：对应表Hot_UserBaseInfo 实体：UserBaseInfoModel
 * Created by lgh on 2015/8/26.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Hot_UserBaseInfo")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UB_UserID")
    private Integer id;

    /**
     * 用户名
     */
    @Column(nullable = false, name = "UB_UserLoginName")
    private String username;

    /**
     * 密码
     */
    @Column(name = "UB_UserLoginPassword")
    private String password;

    /**
     * 手机号
     */
    @Column(name = "UB_UserMobile")
    private String mobile;

    /**
     * 真实姓名
     */
    @Column(name = "UB_UserRealName")
    private String realName;

    /**
     * 微信头像
     */
    @Column(name = "UB_WxHeadImg")
    private String wxHeadUrl;

    /**
     * 微信昵称
     */
    @Column(name = "UB_WxNickName")
    private String wxNickName;

    /**
     * 注册时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "UB_UserRegTime")
    private Date regTime;

    /**
     * 用户类型  0表示普通会员，1表示小伙伴(分销商)，-1表示超级小伙伴
     */
    @Column(name = "UB_UserType")
    private Integer type;

    /**
     * 所属商家
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "UB_CustomerID")
    private Merchant merchant;

    /**
     * 用户头像
     */
    @Column(name = "UB_UserFace")
    private String userFace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getWxHeadUrl() {
        return wxHeadUrl;
    }

    public void setWxHeadUrl(String wxHeadUrl) {
        this.wxHeadUrl = wxHeadUrl;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }


}
