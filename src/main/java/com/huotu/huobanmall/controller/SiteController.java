package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * web网页
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class SiteController {
    public static final int PAGE_SIZE=10;
    @Autowired
    OrderService orderService;


    @RequestMapping("/saleList")
    public String saleList() {
        AppPublicModel appPublicModel = PublicParameterHolder.getParameters();
        appPublicModel.getCurrentUser().getId();
        return "x";
    }


}
