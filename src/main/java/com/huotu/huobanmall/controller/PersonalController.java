package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.PersonalSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lgh on 2015/8/27.
 * Update by shiliting on 2015/8/31
 */
@Controller
@RequestMapping("/app")
public class PersonalController implements PersonalSystem {
    @Autowired
    MerchantRepository merchantRepository;

    @Override
    @RequestMapping("/updateMerchantProfile")
    public ApiResult updateMerchantProfile(Output<AppMerchantModel> user, int profileType, @RequestParam Object profileData) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/getMerchantProfile")
    public ApiResult getMerchantProfile(Output<AppMerchantModel> user) throws Exception {
        AppPublicModel appPublicModel = PublicParameterHolder.getParameters();
        Merchant merchant=merchantRepository.findOne(appPublicModel.getCurrentUser().getId());
        AppMerchantModel appMerchantModel=new AppMerchantModel();
        appMerchantModel.setTitle(merchant.getTitle());
        appMerchantModel.setDiscription(merchant.getDiscription());
        appMerchantModel.setLogo(merchant.getLogo());
        appMerchantModel.setName(merchant.getName());
        appMerchantModel.setPassword(merchant.getPassword());
        appMerchantModel.setNickName(merchant.getNickName());
        user.outputData(appMerchantModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


}
