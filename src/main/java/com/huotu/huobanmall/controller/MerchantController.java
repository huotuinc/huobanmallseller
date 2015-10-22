/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.controller;

import com.huotu.common.EnumHelper;
import com.huotu.common.StringHelper;
import com.huotu.common.SysRegex;
import com.huotu.common.exceptions.InterrelatedException;
import com.huotu.common.model.CodeType;
import com.huotu.common.model.VerificationType;
import com.huotu.common.service.VerificationService;
import com.huotu.huobanmall.api.MerchantSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.ConfigAppVersion;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.model.app.AppGlobalModel;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.model.app.AppUpdateModel;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.MerchantService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Random;

/**
 * Created by lgh on 2015/8/19.
 */
@Controller
@RequestMapping("/app")
public class MerchantController implements MerchantSystem {

    private static Log log = LogFactory.getLog(MerchantController.class);

    @Autowired
    private AppGlobalModel appGlobalModel;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ConfigAppVersionRepository configAppVersionRepository;


    @Autowired
    private MerchantService merchantService;

    @Autowired
    private Environment env;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private AppUpdateModel versionChecking(String opertion, String version, String imei) {
        AppUpdateModel result = new AppUpdateModel();

        if (opertion.equals("HB2015AD")) {

            ConfigAppVersion newestVersionModel = configAppVersionRepository.findTopByOrderByIdDesc();

            if (!SysRegex.IsVersion(version)) // 版本不正确强制整包更新
            {
                result.setUpdateType(CommonEnum.VersionUpdateType.NO);
                result.setUpdateUrl("");
                result.setUpdateTips("");
                result.setUpdateMD5("");
                return result;
            }

            // 获取当前App版本
            ConfigAppVersion currentVersionModel = configAppVersionRepository.findTopByVersionNo(version);

            // 版本不正确强制整包更新
            if (currentVersionModel == null) {
                result.setUpdateType(CommonEnum.VersionUpdateType.NO);
                result.setUpdateUrl("");
                result.setUpdateTips("");
                result.setUpdateMD5("");
                return result;
            }
            // 当前版本跟最新版本一样，无需更新
            if (currentVersionModel.getVersionNo().equals(newestVersionModel.getVersionNo())) {
                result.setUpdateType(CommonEnum.VersionUpdateType.NO);
                result.setUpdateUrl("");
                result.setUpdateTips("");
                result.setUpdateMD5("");
                return result;
            }
            // 当前版本存在致命错误 强制整包更新
            if (currentVersionModel.isBigError()) {
                // 整包更新
                result.setUpdateType(CommonEnum.VersionUpdateType.FORCE_WHOLE);
                result.setUpdateUrl(newestVersionModel.getDescription());
                result.setUpdateTips(newestVersionModel.getDescription());
                result.setUpdateMD5(newestVersionModel.getMd5value());
                return result;

            }

            // 设置默认的更新模式 增量更新 增量包地址

            result.setUpdateType(CommonEnum.VersionUpdateType.INCREMENT);
            result.setUpdateUrl(currentVersionModel.getDifferenceFileUrl());
            result.setUpdateTips(newestVersionModel.getDescription());
            result.setUpdateMD5(currentVersionModel.getMd5value());

            // 获取全局 强制客户端更新（针对用户所有版本）

            Boolean isAppForcedUpdating = false;

            // 检查版本跨度 如2.1.6 版本前2位变化则整包更新 否则只增量更新
            Boolean isBigChange = true;// 1.2.1 版本修改
            if (currentVersionModel.getVersionNo().substring(0, currentVersionModel.getVersionNo().lastIndexOf(".") + 1) != newestVersionModel.getVersionNo()
                    .substring(0, newestVersionModel.getVersionNo().lastIndexOf(".") + 1))
                isBigChange = true;

            if (isAppForcedUpdating && isBigChange)// 强制整包
            {
                result.setUpdateType(CommonEnum.VersionUpdateType.FORCE_WHOLE);
                result.setUpdateUrl(newestVersionModel.getSourceFileUrl());
                result.setUpdateMD5(newestVersionModel.getMd5value());
            } else if (isAppForcedUpdating && !isBigChange)// 强制增量
            {
                result.setUpdateType(CommonEnum.VersionUpdateType.FORCE_INCREMENT);
                result.setUpdateUrl(currentVersionModel.getDifferenceFileUrl());
                result.setUpdateMD5(currentVersionModel.getMd5value());
            } else if (!isAppForcedUpdating && isBigChange)// 整包
            {
                result.setUpdateType(CommonEnum.VersionUpdateType.WHOLE);
                result.setUpdateUrl(newestVersionModel.getSourceFileUrl());
                result.setUpdateMD5(newestVersionModel.getMd5value());
            }

            return result;
        } else {
            result.setUpdateType(CommonEnum.VersionUpdateType.NO);
            result.setUpdateUrl("");
            result.setUpdateTips("");
            result.setUpdateMD5("");
            return result;
        }

    }

    @Override
    @RequestMapping("init")
    public ApiResult init(Output<AppGlobalModel> global, Output<AppMerchantModel> user
            , Output<AppUpdateModel> update) throws Exception {

        AppPublicModel apm = PublicParameterHolder.getParameters();
        update.outputData(versionChecking(apm.getOperation(), apm.getVersion(), apm.getImei()));

        Merchant merchant = apm.getCurrentUser();
        if (merchant == null)
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_USER_LOGIN_FAIL);

        user.outputData(merchantService.getAppMerchantModel(apm.getCurrentOprator(), apm.getCurrentUser()));


        global.outputData(appGlobalModel);

        appGlobalModel.setVoiceSupported(verificationService.supportVoice());

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/login")
    @Transactional
    public ApiResult login(Output<AppMerchantModel> user, String username, String password) throws Exception {

        AppPublicModel appPublicModel = PublicParameterHolder.getParameters();

        AppMerchantModel appMerchantModel = merchantService.login(username, password, appPublicModel);
        if (appMerchantModel == null) {
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_WRONG_USERNAME_PASSWORD);
        }

        user.outputData(appMerchantModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/forgetPassword")
    public ApiResult forgetPassword(String phone, String password, String authcode) throws Exception {
        Date date = new Date();

        if (!env.acceptsProfiles("test")) {
            if (!verificationService.verifyCode(phone, VerificationService.VerificationProject.fanmore, authcode, date, VerificationType.BIND_LOGINPASSWORD)) {
                return ApiResult.resultWith(CommonEnum.AppCode.ERROR_VERIFICATION_CODE);
            }
        }

        Operator operator = operatorRepository.findByNameAndState(phone,0);
        if (operator == null)
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_NO_EXIST_USERNAME);

        operator.setPassword(password);
        operatorRepository.save(operator);

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/sendSMS")
    public ApiResult sendSMS(Output<Boolean> voiceAble, String phone, int type
            , @RequestParam(required = false) Integer codeType)
            throws Exception {
        voiceAble.outputData(verificationService.supportVoice());
        AppPublicModel pms = PublicParameterHolder.getParameters();
        VerificationType verificationType = EnumHelper.getEnumType(VerificationType.class, type);


        Date date = new Date();


        // **********************************************************
        // 发送短信前处理
        if (!SysRegex.IsValidMobileNo(phone)) {
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_WRONG_MOBILE);
        }

        // 重置密码的处理
        if (type == VerificationType.BIND_LOGINPASSWORD.getValue()) {
            Operator operator = operatorRepository.findByNameAndState(phone,0);

            if (operator == null) {
                return ApiResult.resultWith(CommonEnum.AppCode.ERROR_NO_EXIST_USERNAME);
            }
//
//            if (!operator.isEnabled()) {
//                return ApiResult.resultWith(CommonEnum.AppCode.SYSTEM_BAD_ACCOUNT);
//            }

        }


        Random rnd = new Random();
        String code = StringHelper.RandomNum(rnd, 4);

        try {
            verificationService.sendCode(phone, VerificationService.VerificationProject.fanmore, code, date, verificationType, codeType != null ? EnumHelper.getEnumType(CodeType.class, codeType) : CodeType.text);
            return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
        } catch (IllegalStateException ex) {
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_WRONG_VERIFICATION_INTERVAL);
        } catch (IllegalArgumentException ex) {
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_WRONG_MOBILE);
        } catch (NoSuchMethodException ex) {
            //发送类别不受支持！
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_SEND_MESSAGE_FAIL);
        } catch (InterrelatedException ex) {
            //第三方错误！
            log.error("短信发送失败", ex);
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_SEND_MESSAGE_FAIL);
        }
    }

    @Override
    @RequestMapping("/modifyPassword")
    public ApiResult modifyPassword(Output<AppMerchantModel> user, String oldPassword, String newPassword) throws Exception {
        AppPublicModel pms = PublicParameterHolder.getParameters();
        if (pms.getCurrentOprator() != null) {
            Operator operator = pms.getCurrentOprator();
            if (!oldPassword.equals(operator.getPassword())) {
                return ApiResult.resultWith(CommonEnum.AppCode.ERROR_WRONG_PASSWORD);
            }

            operator.setPassword(newPassword);
            operatorRepository.save(operator);
        } else {
            Merchant merchant = pms.getCurrentUser();
            if (!oldPassword.equals(merchant.getPassword())) {
                return ApiResult.resultWith(CommonEnum.AppCode.ERROR_WRONG_PASSWORD);
            }

            merchant.setPassword(newPassword);
            merchantRepository.save(merchant);
        }


        if (pms.getCurrentOprator() == null) {
            user.outputData(merchantService.getAppMerchantModel(false, pms.getCurrentUser().getId()));
        } else {
            user.outputData(merchantService.getAppMerchantModel(true, pms.getCurrentOprator().getId()));
        }

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @RequestMapping("/updateDeviceToken")
    @Override
    @org.springframework.transaction.annotation.Transactional
    public ApiResult updateDeviceToken(String deviceToken) throws Exception {
        AppPublicModel pms = PublicParameterHolder.getParameters();
        log.debug(pms.getImei() + " updateDeviceToken to " + deviceToken);
        pms.getCurrentDevice().setPushingToken(deviceToken);
        deviceRepository.save(pms.getCurrentDevice());
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
