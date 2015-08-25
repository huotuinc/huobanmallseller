package com.huotu.huobanmall.model.app;

/**
 * 商家信息
 * Created by Administrator on 2015/8/24.
 */
public class AppMerchantModel {
    /**
     * 登录名
     */
    private String name;

    /**
     * 昵称 显示在app中
     */
    private String nickName;

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
     * 订单支付成功通知（0关闭,1开启）
     */
    private Number enableBillNotice;
    /**
     * 新增小伙伴通知（0关闭，1开启）
     */
    private Number enablePartnerNotice;

    /**
     * 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     */
    private boolean noDisturbed;

    /**
     * 欢迎提示，包括积分转换信息，来宾转正信息
     */
    private String welcomeTip;
    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递
     * <b>每次App获得新的Token,旧Token就弃用。</b>
     */
    private String token;

    /**
     * 权限，控制app端的内容显示 以,隔开 如 11,33,55
     */
    private String authority;

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

    public boolean isNoDisturbed() {
        return noDisturbed;
    }

    public void setNoDisturbed(boolean noDisturbed) {
        this.noDisturbed = noDisturbed;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
