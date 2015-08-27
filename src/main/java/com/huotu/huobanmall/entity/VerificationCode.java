package com.huotu.huobanmall.entity;



import com.huotu.huobanmall.config.CommonEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证码
 */
@Entity
@Table(indexes = {@Index(columnList = "mobile"),@Index(columnList = "sendTime")})
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 11)
    private String mobile;

    @Column(nullable = false)
    private CommonEnum.VerificationType type;

    private CommonEnum.CodeType codeType;

    @Column(nullable = false,length = 8)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date sendTime;


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

    public CommonEnum.VerificationType getType() {
        return type;
    }

    public void setType(CommonEnum.VerificationType type) {
        this.type = type;
    }

    public CommonEnum.CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CommonEnum.CodeType codeType) {
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
}