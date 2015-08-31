package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.model.app.AppPublicModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * web网页
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class SiteController {
    @RequestMapping("/saleList")
    public String saleList() {
        AppPublicModel appPublicModel = PublicParameterHolder.getParameters();
        appPublicModel.getCurrentUser().getId();
        return "x";
    }

}
