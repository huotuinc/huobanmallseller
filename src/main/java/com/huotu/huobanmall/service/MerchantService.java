package com.huotu.huobanmall.service;

import com.huotu.huobanmall.model.app.AppMerchantModel;

/**
 * Created by lgh on 2015/8/26.
 */
public interface MerchantService {

    AppMerchantModel login(String username, String password) throws Exception;
}
