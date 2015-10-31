/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created by lgh on 2015/9/5.
 */
@Aspect
@Component
public class LoggingAspect {

    private static  Log log = LogFactory.getLog(LoggingAspect.class);

    public LoggingAspect() {
    }

    @PostConstruct
    public void init(){
    }

    @Before("within(com.huotu.huobanmall.controller..*)")
    public void beforeMothod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(methodName + " controller working.");
    }
}
