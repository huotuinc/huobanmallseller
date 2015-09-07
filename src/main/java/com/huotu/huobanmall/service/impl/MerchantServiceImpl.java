package com.huotu.huobanmall.service.impl;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OperatorRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
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

    @Autowired
    private ShopRepository shopRepository;

    public String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public AppMerchantModel login(String username, String password) throws Exception {

        Merchant merchant = merchantRepository.findByName(username);
        if (merchant != null) {
            if (password.equals(merchant.getPassword())) {
                Shop shop = shopRepository.findByMerchant(merchant);

                String token = createToken();

                AppMerchantModel appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(merchant.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority("*");
                appMerchantModel.setToken(merchant.getToken());
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(shop.getLogo());
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(shop.getTitle());


                merchant.setToken(token);
                merchantRepository.save(merchant);
                return appMerchantModel;
            }
        } else {
            Operator operator = operatorRepository.findByName(username);
            if (operator != null && password.equals(operator.getPassword())) {
                String token = createToken();
                Shop shop = shopRepository.findByMerchant(operator.getMerchant());

                AppMerchantModel appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(operator.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority(operator.getAuthority());
                appMerchantModel.setToken(operator.getToken());
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(shop.getLogo());
                appMerchantModel.setNickName(operator.getMerchant().getNickName());
                appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(operator.getName());
                appMerchantModel.setTitle(shop.getTitle());

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
                Shop shop = shopRepository.findByMerchant(merchant);

                appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(merchant.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority("*");
                appMerchantModel.setToken(merchant.getToken());
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(shop.getLogo());
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(shop.getTitle());
                return appMerchantModel;
            }
        } else {
            Operator operator = operatorRepository.findOne(id);
            if (operator != null) {
                Shop shop = shopRepository.findByMerchant(operator.getMerchant());

                appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(operator.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority(operator.getAuthority());
                appMerchantModel.setToken(operator.getToken());
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setIsOperator(false);
                appMerchantModel.setLogo(shop.getLogo());
                appMerchantModel.setNickName(operator.getMerchant().getNickName());
                appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(operator.getName());
                appMerchantModel.setTitle(shop.getTitle());
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
     * @param shop        店铺
     * @param profileType 0:店铺名称 1:店铺描述 2:店铺logo 3:昵称 4:订单支付成功通知（0关闭,1默认开启）
     *                    5：新增小伙伴通知（0关闭，1默认开启） 6: 夜间免打扰模式 0 关闭 1 默认开启 （app端维护具体时间22:00-8:00）
     * @param profileData 0:String 1:String 2:Base64(Image) 3:String 4:Number 5:Number 6:Number
     */
    @Override
    public void updateMerchantProfile(Merchant merchant, Operator operator, Shop shop, int profileType, Object profileData) throws IOException {

        switch (profileType) {
            case 0:
                shop.setTitle(profileData.toString());
                shopRepository.saveAndFlush(shop);
                break;
            case 1:
                shop.setDiscription(profileData.toString());
                shopRepository.saveAndFlush(shop);
            case 2:

                byte[] bytes = StringHelper.toByteArray(profileData);
                ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

                //保存图片 todo 保存图片并获取地址
                String logo = "";
                shop.setLogo(logo);
                shopRepository.saveAndFlush(shop);
            case 3:
                merchant.setNickName(profileData.toString());
                merchantRepository.saveAndFlush(merchant);
            case 4:
                if (operator == null) {
                    merchant.setEnableBillNotice("1".equals(profileData.toString()));
                    merchantRepository.saveAndFlush(merchant);
                } else {
                    operator.setEnableBillNotice("1".equals(profileData.toString()));
                    operatorRepository.saveAndFlush(operator);
                }
            case 5:
                if (operator == null) {
                    merchant.setEnablePartnerNotice("1".equals(profileData.toString()));
                    merchantRepository.saveAndFlush(merchant);
                } else {
                    operator.setEnablePartnerNotice("1".equals(profileData.toString()));
                    operatorRepository.saveAndFlush(operator);
                }
            case 6:
                if (operator == null) {
                    merchant.setNoDisturbed("1".equals(profileData.toString()));
                    merchantRepository.saveAndFlush(merchant);
                } else {
                    operator.setEnablePartnerNotice("1".equals(profileData.toString()));
                    operatorRepository.saveAndFlush(operator);
                }
        }
    }


}
