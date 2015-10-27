/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * todo context.xml需要配置
 * Created by lgh on 2015/9/23.
 */

@Service
public class CommonConfigServiceImpl implements CommonConfigService {

    @Autowired
    Environment env;

    @Override
    public String getResoureServerUrl() {
        return env.getProperty("huobanmall.resources.url", "http://res.51flashmall.com");
    }

    @Override
    public String getMallApiServerUrl() {
        return env.getProperty("huobanmall.mapapi.url", "http://mallapi.huobanj.cn");
    }

    @Override
    public String getWebUrl() {
        return env.getProperty("huobanmall.web.url", "http://apitest.51flashmall.com:8080/huobanmall/");
    }

    @Override
    public String getWebCustomerServicePhone() {
        return env.getProperty("huobanmall.customerServicePhone", "4001818357");
    }


}
