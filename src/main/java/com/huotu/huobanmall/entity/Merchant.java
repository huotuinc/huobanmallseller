package com.huotu.huobanmall.entity;


import javax.persistence.*;

/**
 * 商家 todo 字段长度类型等需要最后跟数据库一致
 * 说明：对应表 Swt_CustomerManage 实体类UserInfoModel
 * Created by lgh on 2015/8/26.
 */
@Entity
@Cacheable(value = false)
@Table(name = "Swt_CustomerManage")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SC_UserID")
    private Integer id;


    /**
     * 登录名
     */
    @Column(name = "SC_UserLoginName")
    private String name;


    /**
     * 密码
     */
    @Column(name = "SC_UserLoginPassword")
    private String password;
    /**
     * 所属商户
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Merchant merchant;

    /**
     * 昵称
     */
    @Column(name = "SC_UserNickName")
    private String nickName;


    /**
     * 手机号
     */
    @Column(name = "SC_PhoneNumber")
    private String mobile;
    /**
     * 订单支付成功通知（0关闭,1默认开启）
     * todo new
     */
    private boolean enableBillNotice;
    /**
     * 新增小伙伴通知（0关闭，1默认开启）
     * todo new
     */
    private boolean enablePartnerNotice;

    /**
     * 夜间免打扰模式 0 关闭 1 默认开启 （app端维护具体时间22:00-8:00）
     * todo new
     */
    private boolean noDisturbed;


    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递
     * <b>每次App获得新的Token,旧Token就弃用。</b>
     * todo new
     */
    @Column(length = 32,name = "AppToken")
    private String token;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
