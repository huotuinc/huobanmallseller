/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.repository.OrderItemsRepository;
import com.huotu.huobanmall.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单项
 * Created by shiliting on 2015/8/27.
 */

@Service
public class OrderItemsServiceImpl implements OrderItemsService{

    @Autowired
    OrderItemsRepository orderItemsRepository;

//    @Override
//    public Page<Object[]> countTopGoodList(Merchant merchant, Pageable pageable) {
//        return orderItemsRepository.countTopGoods(merchant,pageable);
//    }
}
