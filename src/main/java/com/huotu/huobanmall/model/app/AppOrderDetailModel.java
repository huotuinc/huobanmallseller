package com.huotu.huobanmall.model.app;

import java.util.Date;
import java.util.List;

/**
 * 订单管理详情
 * Created by lgh on 2015/9/15.
 */
public class AppOrderDetailModel {

    /**
     * 购买人
     */
    private String buyer;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 规格数据
     */
    private List<AppOrderListProductModel> list;

    /**
     * 返利积分
     */
    private Integer score;

    /**
     * 商品数量
     */
    private Integer amount;


    /**
     * 实付金额
     */
    private float paid;


}
