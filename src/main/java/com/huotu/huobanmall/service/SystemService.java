/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service;

import java.net.URI;

/**
 * @author CJ
 */
public interface SystemService {


    /**
     * 获取系统配置
     * @param key
     * @param defaultValue 如果系统没有设置该值
     * @return
     */
    String systemConfigVor(String key, String defaultValue);

    /**
     * 设置系统配置
     * @param key
     * @param value
     */
    void putSystemConfig(String key, String value);


}
