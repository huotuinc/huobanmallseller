package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppPublicModel;

import java.io.IOException;

/**
 * Created by lgh on 2015/8/26.
 */
public interface MerchantService {
    AppMerchantModel login(String username, String password,AppPublicModel appPublicModel) throws Exception;

    /**
     * 返回app商家信息
     *
     * @param isOperator 是否是操作员 0 否 1是
     * @param id         商家id或操作员id
     * @return
     * @throws Exception
     */
    AppMerchantModel getAppMerchantModel(boolean isOperator, Integer id) throws Exception;

    /**
     * 返回app商家信息
     * @param operator 操作员
     * @param merchant 商家
     * @return
     * @throws Exception
     */
    AppMerchantModel getAppMerchantModel(Operator operator, Merchant merchant) throws Exception;

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
    void updateMerchantProfile(Merchant merchant, Operator operator, Shop shop, int profileType, Object profileData) throws IOException;
}
