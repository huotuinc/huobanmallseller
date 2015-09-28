package com.huotu.huobanmall.entity;

import com.huotu.common.model.AppOS;

import javax.persistence.*;

/**
 * 渠道
 */
@Entity
public class CPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * cpa编码
     */
    @Column(length = 20)
    private String code;
    /**
     * cpa名称
     */
    @Column(length = 20)
    private String name;
    /**
     * cpa来源 如 HB2015AD
     */
    @Column(length = 20)
    private String operation;
    @Column(nullable = false)
    private AppOS os;

    public CPA() {
    }

    public CPA(String code, String name, String operation, AppOS os) {
        this.code = code;
        this.name = name;
        this.operation = operation;
        this.os = os;
    }

    public CPA(String code, String operation) {
        this.code = code;
        this.name = "Unnamed";
        this.operation = operation;
        this.os = toOS();
    }

    public AppOS toOS(){
        if (operation==null)
            return AppOS.Android;
        if (operation.endsWith("AD")){
            return AppOS.Android;
        }
        if (operation.endsWith("AP")){
            return AppOS.iOS;
        }
        if (operation.endsWith("IP")){
            return AppOS.iOS;
        }
        if (operation.endsWith("APJ")){
            return AppOS.iOS;
        }
        if (operation.endsWith("IPJ")){
            return AppOS.iOS;
        }
        // windows Phone not support yet
        return AppOS.Android;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public AppOS getOs() {
        return os;
    }

    public void setOs(AppOS os) {
        this.os = os;
    }
}