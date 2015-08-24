package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppGlobalModel;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppUpdateModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 商户系统
 * Created by Administrator on 2015/8/24.
 */

public interface MerchantSystem {

    /**
     * 初始化接口
     * <p>如果之前登录的用户没有绑定手机服务端无需返回user</p>
     *
     * <b>负责人：罗国华</b>
     * @param global 公共信息
     * @param user 用户信息
     * @param update 更新信息
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult init(
            Output<AppGlobalModel> global,
            Output<AppMerchantModel> user,
            Output<AppUpdateModel> update
    ) throws Exception;


    /**
     * 登录
     *
     * <b>负责人：罗国华</b>
     * @param user 用户信息
     * @param username 用户名(3-50)
     * @param password 一次MD5运算过的密码以16进制描述，英文小写
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult login(Output<AppMerchantModel> user,
                    String username,
                    String password
    ) throws Exception;


    /**
     * 忘记密码
     *
     * <b>负责人：罗国华</b>
     * @param phone String(11)
     * @param password 一次MD5运算过的密码以16进制描述，英文小写
     * @param authcode 验证码
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult forgetPassword(String phone,
                             String password,
                             String authcode
    ) throws Exception;

    /**
     * 获取验证码
     *
     * <b>负责人：</b>
     * @param voiceAble 是否支持语音播报
     * @param phone String(11)
     * @param type 类型 2：忘记密码 3:绑定手机
     * @param codeType 0文本 1语音
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult sendSMS(Output<Boolean> voiceAble,String phone,
                      int type,Integer codeType
    ) throws Exception;



}
