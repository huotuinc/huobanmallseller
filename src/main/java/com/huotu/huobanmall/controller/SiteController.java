package com.huotu.huobanmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * web网页
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/web")
public class SiteController {
    @RequestMapping("/saleList")
    public String saleList() {
        return "x";
    }

}
