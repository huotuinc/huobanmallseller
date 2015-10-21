/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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
