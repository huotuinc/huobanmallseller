package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import com.huotu.huobanmall.repository.RebateRepository;
import com.huotu.huobanmall.service.RebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by lgh on 2015/9/1.
 */

@Service
public class RebateServiceImpl implements RebateService {
    @Autowired
    RebateRepository rebateRepository;
    @Override
    public Page<Rebate> showTopScore(Merchant merchant, Integer status) {
        return rebateRepository.findByMerchantAndStatusOrderByScoreDesc(merchant,status,new PageRequest(0,20));
    }

}
