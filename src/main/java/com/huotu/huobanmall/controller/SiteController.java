package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.concurrency.impl.SystemCountingImpl;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.service.OrderService;
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


    private SystemCountingImpl systemCounting;

    @RequestMapping("/init")
    public String init() throws ParseException {

        systemCounting.InitHistoryDayAndToday();
        return "finished";
    }
}
