package com.huotu.huobanmall.service;

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
     * @param orderStatus   订单的类型
     * @return              订单信息集合
     */
    Page<Order> searchOrders(Integer merchantId,Date time,Integer pageSize,Integer orderStatus,String keyword);

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
     * 返回会员消费额列表前二十
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



}
