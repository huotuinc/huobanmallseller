package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.MerchantSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppGlobalModel;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppUpdateModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/8/19.
 */

@Controller
@RequestMapping("/user")
public class UserController  implements MerchantSystem {

    @RequestMapping("/login")
    public String login() {
        return "abc";
    }

    @Override
    public ApiResult init(Output<AppGlobalModel> global, Output<AppMerchantModel> user, Output<AppUpdateModel> update) throws Exception {
        return null;
    }

    @Override
    public ApiResult login(Output<AppMerchantModel> user, String username, String password) throws Exception {
        return null;
    }

    @Override
    public ApiResult forgetPassword(String phone, String password, String authcode) throws Exception {
        return null;
    }

    @Override
    public ApiResult sendSMS(Output<Boolean> voiceAble, String phone, int type, Integer codeType) throws Exception {
        return null;
    }
}
