package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.MerchantSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppGlobalModel;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppUpdateModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lgh on 2015/8/19.
 */
@Controller
@RequestMapping("/app")
public class MerchantController implements MerchantSystem {

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    @RequestMapping("init")
    public ApiResult init(Output<AppGlobalModel> global, Output<AppMerchantModel> user, Output<AppUpdateModel> update) throws Exception {
        return null;
    }


    @Override
    @RequestMapping("/login")
    public ApiResult login(Output<AppMerchantModel> user, String username, String password) throws Exception {

        Merchant merchant = new Merchant();
        merchant.setName("huotu");
        merchant.setNickName("火图");

        merchantRepository.save(merchant);


        AppMerchantModel appMerchantModel = new AppMerchantModel();
        appMerchantModel.setAuthority("abc");
        user.outputData(appMerchantModel);
        return ApiResult.resultWith(CommonEnum.AppCode.ERROR_VERIFICATION_CODE_OVERDUE);
    }

    @Override
    @RequestMapping("/forgetPassword")
    public ApiResult forgetPassword(String phone, String password, String authcode) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/sendSMS")
    public ApiResult sendSMS(Output<Boolean> voiceAble, String phone, int type, Integer codeType) throws Exception {
        return null;
    }
}
