package com.huotu.huobanmall.entity;


import javax.persistence.*;

/**
 * 商家 todo 字段长度类型等需要最后跟数据库一致
 * Created by lgh on 2015/8/26.
 */
@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 登录名
     */
    @Column(length = 100)
    private String name;


    /**
     * 密码
     */
    @Column(length = 50)
    private String password;
    /**
     * 昵称 显示在app中
     */
    @Column(length = 50)
    private String nickName;


    /**
     * 店铺描述
     */
    @Column(length = 200)
    private String discription;

    /**
     * 店铺logo
     */
    @Column(length = 100)
    private String logo;
    /**
     * 手机号
     */
    @Column(length = 11)
    private String mobile;
    /**
     * 订单支付成功通知（0关闭,1开启）
     */
    private boolean enableBillNotice;
    /**
     * 新增小伙伴通知（0关闭，1开启）
     */
    private boolean enablePartnerNotice;

    /**
     * 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     */
    private boolean noDisturbed;


    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递
     * <b>每次App获得新的Token,旧Token就弃用。</b>
     */
    @Column(length = 32)
    private String token;

    /**
     * 权限，控制app端的内容显示 以,隔开 如 11,33,55
     */
    @Column(length = 500)
    private String authority;

    /**
     * 可用
     */
    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isEnableBillNotice() {
        return enableBillNotice;
    }

    public void setEnableBillNotice(boolean enableBillNotice) {
        this.enableBillNotice = enableBillNotice;
    }

    public boolean isEnablePartnerNotice() {
        return enablePartnerNotice;
    }

    public void setEnablePartnerNotice(boolean enablePartnerNotice) {
        this.enablePartnerNotice = enablePartnerNotice;
    }

    public boolean isNoDisturbed() {
        return noDisturbed;
    }

    public void setNoDisturbed(boolean noDisturbed) {
        this.noDisturbed = noDisturbed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
