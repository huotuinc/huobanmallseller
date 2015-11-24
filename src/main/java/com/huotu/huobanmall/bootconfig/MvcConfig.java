/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.bootconfig;

import com.huotu.huobanmall.interceptor.ApiResultHandler;
import com.huotu.huobanmall.interceptor.AppHandlerExceptionResolver;
import com.huotu.huobanmall.interceptor.CommonInterceptor;
import com.huotu.huobanmall.interceptor.OutputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

/**
 * Created by lgh on 2015/8/19.
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.huotu.huobanmall.controller")

public class MvcConfig extends WebMvcConfigurerAdapter {


    /**
     * 设置servlethandler
     *
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Override
//    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
//
//    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        super.configureMessageConverters(converters);
//    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        super.extendMessageConverters(converters);
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new AppHandlerExceptionResolver());
    }


    @Bean
    CommonInterceptor commonInterceptor (){
        return new CommonInterceptor();
    }

    @Autowired
    private CommonInterceptor commonInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AbstractMethodError abstractMethodError;
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/app/*")
                .excludePathPatterns("/app/forgetPassword")
                .excludePathPatterns("/app/sendSMS");
    }

    /**
     * 设置控制器方法参数化输出
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new OutputHandler());
    }

    /**
     * 监听 控制器的ApiResult返回值
     *
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new ApiResultHandler());
    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(viewResolver());

        registry.jsp();
    }


    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        SpringTemplateEngine engine = new SpringTemplateEngine();
        ServletContextTemplateResolver rootTemplateResolver = new ServletContextTemplateResolver();
        rootTemplateResolver.setPrefix("/");
        rootTemplateResolver.setSuffix(".html");
        rootTemplateResolver.setCharacterEncoding("UTF-8");

        // start cache
//        if(env.acceptsProfiles("dev")){
//            System.out.println("Develement Mode");
//            rootTemplateResolver.setCacheable(false);
//        }

        engine.setTemplateResolver(rootTemplateResolver);

        resolver.setTemplateEngine(engine);
//        resolver.setOrder(99);
        resolver.setOrder(2147483647 + 10);
//        resolver.setViewNames(new String[]{"*.html"});
        resolver.setCharacterEncoding("UTF-8");
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//        resolver.setExcludedViewNames(new String[]{
//                "content/**"
//        });
        return resolver;
    }
}
