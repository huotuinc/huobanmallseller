/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.exception.ShopCloseException;
import com.huotu.huobanmall.exception.ShopExpiredException;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OperatorRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.service.CommonConfigService;
import com.huotu.huobanmall.service.DeviceService;
import com.huotu.huobanmall.service.MallApiService;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanplus.sdk.mall.service.MallInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 商户服务类
 * Created by lgh on 2015/8/26.
 */

@Service
public class MerchantServiceImpl implements MerchantService {

    private static Log log= LogFactory.getLog(MerchantServiceImpl.class);

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CommonConfigService commonConfigService;

    @Autowired
    private MallApiService mallApiService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MallInfoService mallInfoService;

    public String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public AppMerchantModel login(String username, String password, AppPublicModel appPublicModel) throws Exception {


        Merchant merchant = merchantRepository.findByName(username);
        if (merchant != null) {
            if (password.equals(merchant.getPassword())) {
                if(merchant.getMallStatus()!=null&&merchant.getMallStatus()!=1){
                    throw new ShopCloseException("商城已被关闭");
                }
                long date=merchant.getMallExpireDate()==null?0:merchant.getMallExpireDate().getTime();
                if(new Date().getTime()>date){
                    throw new ShopExpiredException("商城已经过期");

                }
                log.debug(" login step 1");
                Shop shop = shopRepository.findByMerchant(merchant);
                log.debug(" login step 2");
                String token = createToken();
                AppMerchantModel appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(merchant.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority("*");
                appMerchantModel.setToken(token);
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setOperatored(false);
                log.debug(" login step 3");
                appMerchantModel.setLogo(commonConfigService.getResoureServerUrl() + shop.getLogo());
                log.debug(" login step 4");
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(shop.getTitle());
                appMerchantModel.setIndexUrl(getIndexUrl(merchant.getId()));
                log.debug(" login step 5");
                //设备变更
                if (merchant.getDevice()==null || !merchant.getDevice().equals(appPublicModel.getCurrentDevice())) {
                    deviceService.userChanged(appPublicModel.getCurrentDevice(), merchant,null, appPublicModel.getVersion(), appPublicModel.getIp());
                }
                log.debug(" login step 6");
                merchant.setToken(token);
                merchantRepository.save(merchant);
                return appMerchantModel;
            }
        } else {

            Operator operator = operatorRepository.findByNameAndState(username,0);
            if(StringUtils.isEmpty(operator)){
                return null;
            }
            merchant=operator.getMerchant();
            if (password.equals(operator.getPassword())) {
                if(merchant.getMallStatus()!=null&&merchant.getMallStatus()!=1){
                    throw new ShopCloseException("商城已被关闭");
                }
                long date=merchant.getMallExpireDate()==null?0:merchant.getMallExpireDate().getTime();
                if(new Date().getTime()>date){
                    throw new ShopExpiredException("商城已经过期");

                }
                String token = createToken();
                Shop shop = shopRepository.findByMerchant(operator.getMerchant());

                AppMerchantModel appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(operator.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority(operator.getAuthority());
                appMerchantModel.setToken(token);
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setOperatored(false);
                appMerchantModel.setLogo(commonConfigService.getResoureServerUrl() + shop.getLogo());
                appMerchantModel.setNickName(operator.getMerchant().getNickName());
                appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(operator.getName());
                appMerchantModel.setTitle(shop.getTitle());
                appMerchantModel.setIndexUrl(getIndexUrl(operator.getMerchant().getId()));

                //设备变更
                if (merchant.getDevice()==null || (!merchant.getDevice().equals(appPublicModel.getCurrentDevice()))) {
                    deviceService.userChanged(appPublicModel.getCurrentDevice(), null,operator, appPublicModel.getVersion(), appPublicModel.getIp());
                }

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
                appMerchantModel.setOperatored(false);
                appMerchantModel.setLogo(commonConfigService.getResoureServerUrl() + shop.getLogo());
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(shop.getTitle());
                appMerchantModel.setIndexUrl(getIndexUrl(merchant.getId()));
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
                appMerchantModel.setOperatored(false);
                appMerchantModel.setLogo(commonConfigService.getResoureServerUrl() + shop.getLogo());
                appMerchantModel.setNickName(operator.getMerchant().getNickName());
                appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(operator.getName());
                appMerchantModel.setTitle(shop.getTitle());
                appMerchantModel.setIndexUrl(getIndexUrl(operator.getMerchant().getId()));
                return appMerchantModel;
            }
        }
        return null;
    }

    @Override
    public AppMerchantModel getAppMerchantModel(Operator operator, Merchant merchant) throws Exception {
        AppMerchantModel appMerchantModel;
        if (operator == null) {
            if (merchant != null) {
                Shop shop = shopRepository.findByMerchant(merchant);
                log.debug("step 7");
                appMerchantModel = new AppMerchantModel();
                appMerchantModel.setName(merchant.getName());
                appMerchantModel.setWelcomeTip("welcome");
                appMerchantModel.setAuthority("*");
                appMerchantModel.setToken(merchant.getToken());
                appMerchantModel.setDiscription(shop.getDiscription());
                appMerchantModel.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
                appMerchantModel.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
                appMerchantModel.setOperatored(false);
                log.debug("step 8");
                appMerchantModel.setLogo(commonConfigService.getResoureServerUrl() + shop.getLogo());
                appMerchantModel.setNickName(merchant.getNickName());
                appMerchantModel.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
                appMerchantModel.setMobile(merchant.getMobile());
                appMerchantModel.setTitle(shop.getTitle());
                log.debug("step 9");
                appMerchantModel.setIndexUrl(getIndexUrl(merchant.getId()));
                log.debug("step 10");
                return appMerchantModel;
            }
        } else {
            Shop shop = shopRepository.findByMerchant(merchant);

            appMerchantModel = new AppMerchantModel();
            appMerchantModel.setName(operator.getName());
            appMerchantModel.setWelcomeTip("welcome");
            appMerchantModel.setAuthority(operator.getAuthority());
            appMerchantModel.setToken(operator.getToken());
            appMerchantModel.setDiscription(shop.getDiscription());
            appMerchantModel.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
            appMerchantModel.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
            appMerchantModel.setOperatored(false);
            appMerchantModel.setLogo(commonConfigService.getResoureServerUrl() + shop.getLogo());
            appMerchantModel.setNickName(merchant.getNickName());
            appMerchantModel.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
            appMerchantModel.setMobile(operator.getName());
            appMerchantModel.setTitle(shop.getTitle());
            appMerchantModel.setIndexUrl(getIndexUrl(merchant.getId()));
            return appMerchantModel;
        }
        return null;
    }


    private String getIndexUrl(Integer merchantId) {

        try {
            return mallApiService.getMsiteUrl(merchantId);
        } catch (IOException e) {
            return null;
        }

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
                mallInfoService.updateNameAndDesc(merchant.getId(), profileData.toString(), shop.getDiscription());
                break;
            case 1:
                mallInfoService.updateNameAndDesc(merchant.getId(), shop.getTitle(), profileData.toString());
                break;
            case 2:
                String logo = mallApiService.upladPic(merchant.getId(), profileData.toString(), 1);
                if (StringUtils.isEmpty(logo)) {

                }
                break;
            case 3:
                merchant.setNickName(profileData.toString());
                merchantRepository.saveAndFlush(merchant);
                break;
            case 4:
                if (operator == null) {
                    merchant.setEnableBillNotice("1".equals(profileData.toString()));
                    merchantRepository.saveAndFlush(merchant);
                } else {
                    operator.setEnableBillNotice("1".equals(profileData.toString()));
                    operatorRepository.saveAndFlush(operator);
                }
                break;
            case 5:
                if (operator == null) {
                    merchant.setEnablePartnerNotice("1".equals(profileData.toString()));
                    merchantRepository.saveAndFlush(merchant);
                } else {
                    operator.setEnablePartnerNotice("1".equals(profileData.toString()));
                    operatorRepository.saveAndFlush(operator);
                }
                break;
            case 6:
                if (operator == null) {
                    merchant.setNoDisturbed("1".equals(profileData.toString()));
                    merchantRepository.saveAndFlush(merchant);
                } else {
                    operator.setNoDisturbed("1".equals(profileData.toString()));
                    operatorRepository.saveAndFlush(operator);
                }
                break;
        }
    }


}
