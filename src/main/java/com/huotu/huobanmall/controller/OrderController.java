package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @RequestMapping("/orderList")
    String orderList(@RequestParam(required = false)Integer orderStatus,
                     @RequestParam(required = false) String lastId,Model model){
        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
        List<Order> list=orderRepository.findAll();
        Page<Order> pages=orderService.searchOrders(merchant.getId(),lastId,PAGE_SIZE,orderStatus);
        model.addAttribute("orderList",pages);
        return "order";
    }




}
