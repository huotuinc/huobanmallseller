package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OperatorRepository;
import com.huotu.huobanmall.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 商户服务类
 * Created by lgh on 2015/8/26.
 */

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public AppMerchantModel login(String username, String password) throws Exception {

        Merchant merchant = merchantRepository.findByName(username);
        if (merchant != null) {
            if (password.equals(merchant.getPassword())) {
                String token = createToken();

                AppMerchantModel appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(merchant.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority("*");
                appMerchantModel.setToken(merchant.getToken());
                appMerchantModel.setDiscription(merchant.getDiscription());
                appMerchantModel.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(merchant.getLogo());
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(merchant.getTitle());

                merchant.setToken(token);
                merchantRepository.save(merchant);
                return appMerchantModel;
            }
        } else {
            Operator operator = operatorRepository.findByName(username);
            if (operator != null && password.equals(operator.getPassword())) {
                String token = createToken();

                AppMerchantModel appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(operator.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority(operator.getAuthority());
                appMerchantModel.setToken(operator.getToken());
                appMerchantModel.setDiscription(operator.getMerchant().getDiscription());
                appMerchantModel.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(operator.getMerchant().getLogo());
                appMerchantModel.setNickName(operator.getNickName());
                appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(operator.getName());
                appMerchantModel.setTitle(operator.getMerchant().getTitle());

                operator.setToken(token);
                operatorRepository.save(operator);
                return appMerchantModel;
            }
        }


        return null;
    }

    @Override
    public AppMerchantModel getAppMerchantModel(boolean isOperator, Integer id) throws Exception {
        AppMerchantModel appMerchantModel = null;
        if (!isOperator) {
            Merchant merchant = merchantRepository.findOne(id);
            if (merchant != null) {
                appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(merchant.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority("*");
                appMerchantModel.setToken(merchant.getToken());
                appMerchantModel.setDiscription(merchant.getDiscription());
                appMerchantModel.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(merchant.getLogo());
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(merchant.getTitle());
                return appMerchantModel;
            }
        } else {
            Operator operator = operatorRepository.findOne(id);
            if (operator != null) {
                appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(operator.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority(operator.getAuthority());
                appMerchantModel.setToken(operator.getToken());
                appMerchantModel.setDiscription(operator.getMerchant().getDiscription());
                appMerchantModel.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(operator.getMerchant().getLogo());
                appMerchantModel.setNickName(operator.getNickName());
                appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(operator.getName());
                appMerchantModel.setTitle(operator.getMerchant().getTitle());
                return appMerchantModel;
            }
        }
        return null;
    }

    /**
     * 修改商家信息
     *
     * @param merchant    商家
     * @param operator    操作员
     * @param profileType 0:店铺名称 1:店铺描述 2:店铺logo 3:昵称 4:订单支付成功通知（0关闭,1开启）
     *                    5：新增小伙伴通知（0关闭，1开启） 6: 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     * @param profileData 0:String 1:String 2:Base64(Image) 3:String 4:Number 5:Number 6:Number
     */
    @Override
    public void updateMerchantProfile(Merchant merchant, Operator operator, int profileType, Object profileData) {

        //商家
        if (operator == null) {
            switch (profileType) {
                case 0:

                    break;

            }
        } else {
            //操作员
        }



    }
}
