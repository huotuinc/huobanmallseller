/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.bootconfig;

import com.huotu.huobanmall.model.app.AppGlobalModel;
import com.huotu.huobanplus.sdk.mall.MallSDKConfig;
import org.luffy.lib.libspring.data.ClassicsRepositoryFactoryBean;
import org.luffy.lib.libspring.logging.LoggingConfig;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by Administrator on 2015/8/19.
 */
@Configuration
@ComponentScan(value = {"com.huotu.huobanmall.service.impl", "com.huotu.common.service.impl","com.huotu.huobanmall.concurrency"})
@ImportResource(value = {"classpath:spring-jpa.xml"})
@EnableJpaRepositories(value = {"com.huotu.huobanmall.repository"}, repositoryFactoryBeanClass = ClassicsRepositoryFactoryBean.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
@Import(
        {
                MallSDKConfig.class,
                LoggingConfig.class
        })
public class RootConfig {

    @Bean
    public AppGlobalModel appGlobalModel() {
        return new AppGlobalModel();
    }

}
