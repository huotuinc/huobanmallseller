package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.model.app.AppMerchantModel;
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
            if(password.equals(merchant.getPassword())) {
                String token = createToken();

                AppMerchantModel model = new AppMerchantModel();
                model.setName(merchant.getName());
                model.setNickName(merchant.getNickName());

                model.setAuthority("*");
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
        } else {
            Operator   operator = operatorRepository.findByName(username);
            if (operator != null && password.equals(operator.getPassword())) {
                String token = createToken();

                AppMerchantModel model = new AppMerchantModel();
                model.setName(operator.getName());
                model.setNickName(operator.getNickName());
                model.setAuthority(operator.getAuthority());
                model.setDiscription(operator.getMerchant().getDiscription());
                model.setEnableBillNotice(operator.isEnableBillNotice() ? 1 : 0);
                model.setEnablePartnerNotice(operator.isEnablePartnerNotice() ? 1 : 0);
                model.setLogo(operator.getMerchant().getLogo());
                model.setMobile(operator.getName());
                model.setNoDisturbed(operator.isNoDisturbed() ? 1 : 0);
                model.setToken(token);
                model.setWelcomeTip("welcome");

                operator.setToken(token);
                operatorRepository.save(operator);
                return model;
            }
        }


        return null;
    }
}
