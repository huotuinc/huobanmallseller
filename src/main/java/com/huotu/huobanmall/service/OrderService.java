/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.MainOrder;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface OrderService {
    /**
     * Create by shiliting on 2015/28/28
     * 显示订单信息
     * @param merchantId     所属商家ID
     * @param time          最后一个显示的下单时间
     * @param pageSize      一次显示订单的数量
     * @return              订单信息集合
     */
    Page<Order> searchOrdersDetail(Integer merchantId,Date time,Integer pageSize,String keyword);

    /**
     * Create by shiliting on 2015/9/23
     * 显示订单信息
     * @param merchantId     所属商家ID
     * @param time          最后一个显示的下单时间
     * @param pageSize      一次显示订单的数量
     * @param orderStatus   订单的类型
     * @return              订单信息集合
     */
    List<Order> searchOrders(Integer merchantId,Date time,Integer pageSize,Integer orderStatus,String keyword);

    /**
     * Create by shiliting on2015/9/25
     * 按照主订单查找订单信息
     * @param merchantId    所属商家ID
     * @param time          最后一条显示主订单的时间
     * @param pageSize      每页几条主订单
     * @param orderStatus   订单分类
     * @param keyword       主订单关键字
     * @return
     */
    List<MainOrder> searchMainOrders(Integer merchantId,Date time,Integer pageSize,Integer orderStatus,String keyword);

    /**
     * Create by shiliting on 2015/9/1
     * 计算订单数量
     * @param merchant      所属商家
     * @param lastTime      大于设定的时间
     * @return              订单数量
     */
    Integer countOrderQuantity(Merchant merchant,Date lastTime);

    /**
     * Create by shiliting on 2015/9/1
     * 计算今日订单数
     * @param merchant      所属商家
     * @return              订单数量
     */
    Integer countOrderQuantity(Merchant merchant);

//    /**
//     * Create by shiliting on 2015/9/1
//     * 计算销售额
//     * @param merchant      所属商家
//     * @param lastTime      大于设定的时间
//     * @return              销售额
//     */
//    float countSale(Merchant merchant,Date lastTime);

    /**
     * Create by shiliting on 2015/9/1
     * 计算今日销售额
     * @param merchant      所属商家
     * @return              销售额
     */
    float countSale(Merchant merchant);

//    /**
//     * Create by shiliting on 2015/9/6
//     * 返回会员积分列表
//     * @param merchant      所属商家
//     * @return              会员积分列表
//     */
//    Page<Rebate> countUserScoreList(Merchant merchant,Pageable pageable);

    /**
     * Create by shiliting on 2015/9/6
     * 返回会员消费额列表前十
     * @param merchant      所属商家
     * @return              会员消费额列表
     */
    Page<Object[]> searchTopExpenditure(Merchant merchant,Pageable pageable);

    /**
     * 返回会员消费额列表
     * @param merchant      所属商家
     * @param time          最后一条显示的时间
     * @return
     */
    List searchExpenditureList(Merchant merchant,String name, Date time, Integer pageSize);

    /**
     * 返回金额最高的前几条订单
     * @param merchant      所属商家
     * @param payStatus     订单支付状态(已支付)
     * @param pageable      订单条数
     * @return
     */
    Page<Order>searchTopOrder(Merchant merchant,Integer payStatus,Pageable pageable);


//    /**
//     * 返回最终订单状态信息
//     * @param order
//     * @param status        订单所属分类
//     * @return
//     */
//    String getFinalOrderStatus(Order order,Integer status);

    /**
     * 获取支付状态码对应的状态信息
     * @param status
     * @return
     */
    String getPayStatus(Integer status);

    /**
     * 获取物流状态码对应的物流信息
     * @param status
     * @return
     */
    String getDeliverStatus(Integer status);

    /**
     * 获取订单码对应的订单信息
     * @param status
     * @return
     */
    String getOrderStatus(Integer status);




}
