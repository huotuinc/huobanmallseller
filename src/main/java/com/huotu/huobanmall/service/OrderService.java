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
     * @param lastOrderTime 显示订单的最后一条的下单时间
     * @param pageSize      一次显示订单的数量
     * @param orderStatus   订单的类型
     * @return
     */
    Page<Order> searchOrders(Integer merchantId,Date lastOrderTime,Integer pageSize,Integer orderStatus);

}
