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
