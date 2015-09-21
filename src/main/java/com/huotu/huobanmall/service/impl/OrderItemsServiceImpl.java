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
