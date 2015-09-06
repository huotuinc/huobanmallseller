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
        System.out.println(222);
    }

    @PostConstruct
    public void init(){
        System.out.println(111);
    }

    @Before("within(com.huotu.huobanmall.controller..*)")
    public void beforeMothod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(methodName + " controller 启动了");
    }
}
