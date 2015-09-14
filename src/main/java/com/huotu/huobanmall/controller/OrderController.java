package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lgh on 2015/8/27.
 * Update by shiliting on 2015/9/5
 */
@Controller
@RequestMapping("/app")
public class OrderController {
    public static final int PAGE_SIZE=10;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

//    @RequestMapping("/orderList")
//    public String orderList(@RequestParam(required = false)Integer orderStatus,
//                            @RequestParam(required = false) Date lastOrderTime,Integer pageSize,Model model){
//        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
//        Page<Order> pages=orderService.searchOrders(merchant.getId(),lastOrderTime,pageSize,orderStatus);
//        model.addAttribute("orderList",pages);
//        return "order";
//    }
//
//    @RequestMapping("/userScoreList")
//    public String userScoreList(Integer pageSize,Model model){
//        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
//        Page<Rebate> page=orderService.countUserScoreList(merchant,new PageRequest(0,pageSize));
//        model.addAttribute("userScoreList",page);
//        return "x";
//    }
//
//    @RequestMapping("/userExpenditureList")
//    public String userExpenditureList(Integer pageSize,Model model){
//        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
//        Page<Object[]> page=orderService.countUserExpenditureList(merchant,new PageRequest(0, pageSize));
//        model.addAttribute("userExpenditureList",page);
//        return "x";
//    }


}
