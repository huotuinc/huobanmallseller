package com.huotu.huobanmall.controller;

import com.huotu.common.SysRegex;
import com.huotu.common.service.VerificationService;
import com.huotu.huobanmall.api.MerchantSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.ConfigAppVersion;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppGlobalModel;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.model.app.AppUpdateModel;
import com.huotu.huobanmall.repository.ConfigAppVersionRepository;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.service.SystemService;
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
    private AppGlobalModel appGlobalModel;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ConfigAppVersionRepository configAppVersionRepository;

    @Autowired
    private SystemService systemService;

    private AppUpdateModel versionChecking(String opertion, String version, String imei) {
        AppUpdateModel result = new AppUpdateModel();

        if (opertion.equals("HuoTu2013AD")) {

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

            Boolean isAppForcedUpdating = "1".equals(systemService.systemConfigVor("AppForcedUpdating", ""));

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

        appGlobalModel.setVoiceSupported(verificationService.supportVoice());
        global.outputData(appGlobalModel);

        AppPublicModel pms = PublicParameterHolder.getParameters();

        update.outputData(versionChecking(pms.getOperation(), pms.getVersion(), pms.getImei()));

        Merchant merchant = pms.getCurrentUser();
        if (merchant == null)
            return ApiResult.resultWith(CommonEnum.AppCode.ERROR_USER_LOGIN_FAIL);

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);

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
