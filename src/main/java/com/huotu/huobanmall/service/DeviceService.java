/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service;


import com.huotu.huobanmall.entity.Device;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.User;

import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author CJ
 */
public interface DeviceService {
    /**
     * 返回设备实例，如果目前没有将创建一个新的实例
     *
     * @param imei      唯一imei码
     * @param code      渠道code
     * @param operation 设备operation
     * @return 设备实例
     */
    @Transactional
    Device fetchDevice(String imei, String code, String operation);

    /**
     * 用户设备变化
     *
     * @param device   当前设备
     * @param merchant
     * @param operator
     * @param version  app版本
     * @param ip       app IP
     */
    @Transactional
    void userChanged(@NotNull Device device, Merchant merchant, Operator operator, String version, String ip);


}
