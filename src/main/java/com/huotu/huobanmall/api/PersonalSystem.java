package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 个性化系统
 */
public interface PersonalSystem {


    /**
     * 更新商家信息
     * <p>
     * <b>负责人：</b>
     * <p>Base64 采用Mime标准(http://www.ietf.org/rfc/rfc2045.txt)</p>
     *
     * @param user        更新后的商家信息
     * @param profileType 0:店铺名称 1:店铺描述 2:店铺logo 3:昵称 4:订单支付成功通知（0关闭,1开启）
     *                    5：新增小伙伴通知（0关闭，1开启） 6: 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     * @param profileData 0:String 1:String 2:Base64(Image) 3:String 4:Number 5:Number 6:Number
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    ApiResult updateProfile(Output<AppMerchantModel> user, int profileType, @RequestParam Object profileData) throws Exception;

}
