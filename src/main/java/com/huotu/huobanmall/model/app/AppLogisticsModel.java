package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class AppLogisticsModel {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息信息
     */
    private String msg;
    /**
     * 物流具体信息
     */
    private List<AppLogisticsDataModel> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AppLogisticsDataModel> getData() {
        return data;
    }

    public void setData(List<AppLogisticsDataModel> data) {
        this.data = data;
    }


}
