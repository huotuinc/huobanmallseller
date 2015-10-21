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
 * Created by Administrator on 2015/9/25.
 */
public class AppMainOrderListModel {

    /**
     * 主订单号
     */
    private  String mainOrderNo;
    /**
     * 子订单列表
     */
    private List<AppOrderListModel> list;

    public String getMainOrderNo() {
        return mainOrderNo;
    }

    public void setMainOrderNo(String mainOrderNo) {
        this.mainOrderNo = mainOrderNo;
    }

    public List<AppOrderListModel> getList() {
        return list;
    }

    public void setList(List<AppOrderListModel> list) {
        this.list = list;
    }
}
