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
