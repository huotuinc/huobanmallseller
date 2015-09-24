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
        return env.getProperty("huobanmall.resources.url", "http://res.51flashmall.com/");
    }

    @Override
    public String getMallApiServerUrl() {
        return env.getProperty("huobanmall.mapapi.url", "http://mallapi.51flashmall.com/");
    }

    @Override
    public String getWebUrl(){
        return env.getProperty("huobanmall.web.url","apitest.51flashmall.com:8080/huobanmall/");
    }

}
