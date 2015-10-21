/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.PersonalSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.Paging;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppMessageModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 个性化系统
 * Created by lgh on 2015/8/27.
 * Update by shiliting on 2015/8/31
 */
@Controller
@RequestMapping("/app")
public class PersonalController implements PersonalSystem {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MallApiService mallApiService;

    /**
     * 更新商家信息
     * <p>
     * <b>负责人：罗国华</b>
     * <p>Base64 采用Mime标准(http://www.ietf.org/rfc/rfc2045.txt)</p>
     *
     * @param user        更新后的商家信息
     * @param profileType 0:店铺名称 1:店铺描述 2:店铺logo 3:昵称 4:订单支付成功通知（0关闭,1开启）
     *                    5：新增小伙伴通知（0关闭，1开启） 6: 夜间免打扰模式 0 关闭 1 默认开启 （app端维护具体时间22:00-8:00）
     * @param profileData 0:String 1:String 2:Base64(Image) 3:String 4:Number 5:Number 6:Number
     * @return
     * @throws Exception
     */
    @Override
    @RequestMapping("/updateMerchantProfile")
    public ApiResult updateMerchantProfile(Output<AppMerchantModel> user, int profileType, @RequestParam Object profileData)
            throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();


      merchantService.updateMerchantProfile(apm.getCurrentUser(), apm.getCurrentOprator(), apm.getCurrentShop(), profileType, profileData);

        if (apm.getCurrentOprator() == null) {
            user.outputData(merchantService.getAppMerchantModel(false, apm.getCurrentUser().getId()));
        } else {
            user.outputData(merchantService.getAppMerchantModel(true, apm.getCurrentOprator().getId()));
        }

//        user.outputData(merchantService.getAppMerchantModel(apm.getCurrentOprator(),apm.getCurrentUser()));


        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/getMerchantProfile")
    public ApiResult getMerchantProfile(Output<AppMerchantModel> user) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();
//        if (apm.getCurrentOprator() == null)
//            user.outputData(merchantService.getAppMerchantModel(false, apm.getCurrentUser().getId()));
//        else
//            user.outputData(merchantService.getAppMerchantModel(true, apm.getCurrentOprator().getId()));

        user.outputData(merchantService.getAppMerchantModel(apm.getCurrentOprator(), apm.getCurrentUser()));

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/messages")
    public ApiResult messages(Output<AppMessageModel[]> messages, Paging paging) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();

        if (apm.getCurrentOprator() == null) {
            messages.outputData(messageService.getMessages(apm.getCurrentUser(), null, paging));
        } else {
            messages.outputData(messageService.getMessages(null, apm.getCurrentOprator(), paging));
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Autowired
    private FeedbackService feedbackService;


    @Override
    @RequestMapping("/feedback")
    public ApiResult feedback(String name, String contact, String content) throws Exception {

        AppPublicModel apm = PublicParameterHolder.getParameters();

        if (apm.getCurrentOprator() == null) {
            feedbackService.insert(name, contact, content, apm.getCurrentUser(), null);
        } else {
            feedbackService.insert(name, contact, content, null, apm.getCurrentOprator());
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

}
