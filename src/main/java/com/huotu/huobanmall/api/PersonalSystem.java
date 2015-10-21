/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.Paging;
import com.huotu.huobanmall.model.app.AppFeedbackModel;
import com.huotu.huobanmall.model.app.AppMerchantModel;
import com.huotu.huobanmall.model.app.AppMessageModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 个性化系统
 */
public interface PersonalSystem {


    /**
     * 更新商家信息
     * <p>
     * <b>负责人：罗国华</b>
     * <p>Base64 采用Mime标准(http://www.ietf.org/rfc/rfc2045.txt)</p>
     *
     * @param user        更新后的商家信息
     * @param profileType 0:店铺名称 1:店铺描述 2:店铺logo 3:昵称 4:订单支付成功通知（0关闭,1开启）
     *                    5：新增小伙伴通知（0关闭，1开启） 6: 夜间免打扰模式 0 关闭 1 默认开启 （app端维护具体时间22:00-8:00）
     * @param profileData 0:String 1:String 2:Base64(Image) 3:String 4:Number 5:Number 6:Number
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    ApiResult updateMerchantProfile(Output<AppMerchantModel> user, int profileType, @RequestParam Object profileData) throws Exception;


    /**
     * 显示商家信息
     * <p>
     * <b>负责人：罗国华</b>
     *
     * @param user 当前商家的信息
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult getMerchantProfile(Output<AppMerchantModel> user) throws Exception;


    /**
     * 消息列表-可分组查询
     * <p>分组依据-messageOrder</p>
     * <p>
     * <b>负责人：刘渠成</b>
     *
     * @param messages
     * @param paging
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult messages(Output<AppMessageModel[]> messages, Paging paging) throws Exception;


    /**
     * 提交审核
     *
     * @param name
     * @param contact
     * @param content
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    ApiResult feedback(String name, String contact, String content) throws Exception;
}
