/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.concurrency.impl.SystemCountingImpl;
import com.huotu.huobanmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

/**
 * web网页
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/web")
public class SiteController {
    @Autowired
    CountDayMemberRepository countDayMemberRepository;

    @Autowired
    CountDaySalesRepository countDaySalesRepository;

    @Autowired
    CountDayPartnerRepository countDayPartnerRepository;

    @Autowired
    CountDayOrderRepository countDayOrderRepository;

    @Autowired
    CountTodaySalesRepository countTodaySalesRepository;

    @Autowired
    CountTodayOrderRepository countTodayOrderRepository;

    @Autowired
    CountTodayMemberRepository countTodayMemberRepository;

    @Autowired
    CountTodayPartnerRepository countTodayPartnerRepository;


    @Autowired
    private SystemCountingImpl systemCounting;

    @RequestMapping("/init")
    public String init() throws ParseException {

        systemCounting.InitHistoryDayAndToday();
//        countDayMemberRepository.deleteAll();
//        countDayOrderRepository.deleteAll();
//        countDayPartnerRepository.deleteAll();
//        countDaySalesRepository.deleteAll();
//        countTodayMemberRepository.deleteAll();
//        countTodayOrderRepository.deleteAll();
//        countTodayPartnerRepository.deleteAll();
//        countTodaySalesRepository.deleteAll();
        return "finished";
    }
}
