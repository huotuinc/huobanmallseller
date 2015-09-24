package com.huotu.huobanmall.model.app;

/**
 * 商户信息
 * Created by lgh on 2015/8/24.
 */
public class AppMerchantModel {
    /**
     * 登录名（操作员或商家的登录名）
     */
    private String name;


    /**
     * 昵称 显示在app中
     */
    private String nickName;

    /**
     * 店铺名称
     */
    private String title;

    /**
     * 店铺描述
     */
    private String discription;

    /**
     * 店铺logo
     */
    private String logo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 订单支付成功通知（0关闭,1默认开启）
     */
    private Number enableBillNotice = 1;
    /**
     * 新增小伙伴通知（0关闭,1默认开启）
     */
    private Number enablePartnerNotice = 1;

    /**
     * 夜间免打扰模式 0 关闭1 默认开启  （app端维护具体时间22:00-8:00）
     */
    private Number noDisturbed = 1;

    /**
     * 欢迎提示
     */
    private String welcomeTip;
    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递
     * <b>每次App获得新的Token,旧Token就弃用。</b>
     */
    private String token;

    /**
     * 权限，控制app端的内容显示 以,隔开 如 11,33,55 特殊情况：*代表商家管理员权限
     */
    private String authority;


    /**
     * 首页地址
     */
    private String indexUrl;


    /**
     * 0 商家 1 操作员
     */
    private boolean operatored;

    public boolean getOperatored() {
        return operatored;
    }

    public void setOperatored(boolean operatored) {
        this.operatored = operatored;
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

    public Number getEnableBillNotice() {
        return enableBillNotice;
    }

    public void setEnableBillNotice(Number enableBillNotice) {
        this.enableBillNotice = enableBillNotice;
    }

    public Number getEnablePartnerNotice() {
        return enablePartnerNotice;
    }

    public void setEnablePartnerNotice(Number enablePartnerNotice) {
        this.enablePartnerNotice = enablePartnerNotice;
    }


    public String getWelcomeTip() {
        return welcomeTip;
    }

    public void setWelcomeTip(String welcomeTip) {
        this.welcomeTip = welcomeTip;
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

    public Number getNoDisturbed() {
        return noDisturbed;
    }

    public void setNoDisturbed(Number noDisturbed) {
        this.noDisturbed = noDisturbed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
}
