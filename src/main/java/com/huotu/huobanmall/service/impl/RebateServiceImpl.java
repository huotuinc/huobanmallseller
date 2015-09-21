package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import com.huotu.huobanmall.repository.RebateRepository;
import com.huotu.huobanmall.service.RebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lgh on 2015/9/1.
 */

@Service
public class RebateServiceImpl implements RebateService {
    @Autowired
    RebateRepository rebateRepository;
    @Override
    public Page<Object[]> searchTopScore(Merchant merchant, Integer status) {
        return rebateRepository.findTopScore(merchant,status,new PageRequest(0,20));


    }

    @Override
    public Page<Rebate> searchUserScore(Merchant merchant, Integer status, Date time) {
        return rebateRepository.findByMerchantAndStatusLessThanOrderByTimeDesc(merchant, status, time, new PageRequest(0, 20));
    }


    @Override
    public String getScoreStatus(Integer status) {
        switch (status){
            case 0:
                return "待转正";
            case 1:
                return "已转正";
            case -1:
                return "待转正状态下被作废";
            case -2:
                return "已转正状态下被作废";
            default:
                return "无";
        }
    }


}
