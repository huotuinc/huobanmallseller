package com.huotu.huobanmall.model.app;

/**
 * Created by Administrator on 2015/9/18.
 */
public class AppLogisticsDataModel {
    /**
     * 快递单号
     */
    private String number;
    /**
     * 快递状态
     */
    private String status;
    /**
     * 动态更新时间
     */
    private String times;
    /**
     * 动态内容
     */
    private String context;
    /**
     * 对应行政区域的名称
     */
    private String areaname;
    /**
     * 对应行政区域的编码
     */
    private String areacode;
    /**
     * 快递公司
     */
    private String company;

    /**
     * 快递公司编码
     */
    private String code;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
