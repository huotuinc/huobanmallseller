package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.PersonalSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lgh on 2015/8/27.
 */
@Controller
@RequestMapping("/app")
public class PersonalController implements PersonalSystem {
    @Override
    @RequestMapping("/updateProfile")
    public ApiResult updateProfile(Output<AppMerchantModel> user, int profileType, @RequestParam Object profileData) throws Exception {
        return null;
    }
}
