package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Order;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface OrderService {
    /**
     * Create by shiliting on 2015/28/28
     * 显示订单信息
     * @param merchantId    商家ID
     * @param lastId        显示订单的最后一条的下单时间
     * @param pageSize      一次显示订单的数量
     * @param orderStatus   订单的类型
     * @return              订单信息集合
     */
    Page<Order> searchOrders(Integer merchantId,String lastId,Integer pageSize,Integer orderStatus);

    /**
     * Create by shiliting on 2015/9/1
     * 计算订单数量
     * @param merchantId    商家ID
     * @param lastTime      大于设定的时间
     * @return              订单数量
     */
    Integer countOrderQuantity(Integer merchantId,Date lastTime);

    /**
     * Create by shiliting on 2015/9/1
     * 计算订单总数
     * @param merchantId    商家ID
     * @return              订单数量
     */
    Integer countOrderQuantity(Integer merchantId);

    /**
     * Create by shiliting on 2015/9/1
     * @param merchantId    商家ID
     * @param lastTime      大于设定的时间
     * @return              销售额
     */
    float countSale(Integer merchantId,Date lastTime);

    /**
     * Create by shiliting on 2015/9/1
     * @param merchantId    商家ID
     * @return              销售额
     */
    float countSale(Integer merchantId);

}
