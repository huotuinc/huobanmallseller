/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huotu.huobanmall.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 公共信息
 * APP端请无视
 * <p>在服务器实例中 它应当是唯一 并且是可维护的</p>
 * Created by lgh on 2015/8/24.
 */

public class AppGlobalModel {
    /**
     * 服务地址
     */
    @JsonIgnore
    private String serverUrl;
    /**
     * 关于我们
     */
    private String aboutURL;
    /**
     * 帮助
     */
    private String helpURL;

    /**
     * 验证码是否支持语音播报
     */
    private boolean voiceSupported;

    /**
     * 客服电话号码
     */
    private String customerServicePhone;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAboutURL() {
        return aboutURL;
    }

    public void setAboutURL(String aboutURL) {
        this.aboutURL = aboutURL;
    }

    public String getHelpURL() {
        return helpURL;
    }

    public void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }

    public boolean isVoiceSupported() {
        return voiceSupported;
    }

    public void setVoiceSupported(boolean voiceSupported) {
        this.voiceSupported = voiceSupported;
    }

    public String getCustomerServicePhone() {
        return customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
        this.customerServicePhone = customerServicePhone;
    }


    @Autowired
    private CommonConfigService commonConfigService;

    @Autowired
    public void setEnv(Environment env) {
        serverUrl = commonConfigService.getWebUrl();
//        serverUrl = env.getProperty("huobanmall.url", "http://test.huobanmall.com");
//        customerServicePhone = env.getProperty("huobanmall.customerServicePhone", "4001818357");

        customerServicePhone = commonConfigService.getWebCustomerServicePhone();
    }


    @PostConstruct
    public void afterConstruct() {
        setAboutURL(serverUrl + "/appabout.html");
        setHelpURL(serverUrl + "/apphelp.html");
    }
}
