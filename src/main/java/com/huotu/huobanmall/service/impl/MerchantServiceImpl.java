package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.repository.MerchantRepository;
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

    public String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public AppMerchantModel login(String username, String password) throws Exception {
        Merchant merchant = merchantRepository.findByName(username);
        if (merchant != null && password.equals(merchant.getPassword())) {
            String token = createToken();

            AppMerchantModel model = new AppMerchantModel();
            model.setName(merchant.getName());
            model.setNickName(merchant.getNickName());
            model.setAuthority(merchant.getAuthority());
            model.setDiscription(merchant.getDiscription());
            model.setEnableBillNotice(merchant.isEnableBillNotice() ? 1 : 0);
            model.setEnablePartnerNotice(merchant.isEnablePartnerNotice() ? 1 : 0);
            model.setLogo(merchant.getLogo());
            model.setMobile(merchant.getMobile());
            model.setNoDisturbed(merchant.isNoDisturbed() ? 1 : 0);
            model.setToken(token);
            model.setWelcomeTip("welcome");

            merchant.setToken(token);
            merchantRepository.save(merchant);
            return model;
        }
        return null;
    }
}
