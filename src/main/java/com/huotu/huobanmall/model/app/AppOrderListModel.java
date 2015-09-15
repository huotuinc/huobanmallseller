package com.huotu.huobanmall.model.app;

import java.util.Date;
import java.util.List;

/**
 * 订单数据 （订单管理）
 * Created by lgh on 2015/9/11.
 */
public class AppOrderListModel {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单状态
     */
    private Integer status;


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


    /**
     * 下单时间
     */
    private Date time;

}
