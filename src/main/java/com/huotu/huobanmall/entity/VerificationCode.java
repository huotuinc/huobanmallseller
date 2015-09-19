package com.huotu.huobanmall.entity;


import com.huotu.common.model.CodeType;
import com.huotu.common.model.VerificationType;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证码
 * 对应Swt_SmsVerification SwtSmsVerificationModel
 */
@Entity
@Cacheable(value = false)
@Table(name = "Swt_SmsVerification", indexes = {@Index(columnList = "mobile"), @Index(columnList = "sendTime")})
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SSV_ID")
    private Long id;

    @Column(name = "SSV_Phone")
    private String mobile;

    private VerificationType type;

    private CodeType codeType;

    @Column(name = "SSV_Verification")
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SSV_AddTime")
    private Date sendTime;

    /**
     * 商家
     */
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "SSV_CustomerId")
    private Merchant merchant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public VerificationType getType() {
        return type;
    }

    public void setType(VerificationType type) {
        this.type = type;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}