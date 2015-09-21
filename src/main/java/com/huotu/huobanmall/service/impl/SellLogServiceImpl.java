package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.SellLogRepository;
import com.huotu.huobanmall.service.SellLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 订单项
 * Created by shiliting on 2015/8/27.
 */

@Service
public class SellLogServiceImpl implements SellLogService{

    @Autowired
    SellLogRepository sellLogRepository;

    @Override
    public Page<Object[]> countTopGoodList(Merchant merchant, Pageable pageable) {
        return sellLogRepository.countTopGoods(merchant.getId(),pageable);
    }
}
