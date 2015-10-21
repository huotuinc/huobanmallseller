/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.test.boot;

import com.huotu.huobanmall.test.aop.AutoFlush;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created by lgh on 2015/9/14.
 */

@Configuration
@ComponentScan(value = {"com.huotu.huobanmall.test.boot"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TestLoader {


    @Bean
    public AutoFlush autoFlush() {
        return new AutoFlush();
    }
}
