package com.huotu.huobanmall.bootconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Administrator on 2015/8/19.
 */
@Configuration
@ComponentScan(value = {"com.huotu.huobanmall.service.impl"})
@ImportResource(value = {"classpath:spring-jpa.xml"})
@EnableJpaRepositories(value = { "com.huotu.huobanmall.repository"})
public class RootConfig {

}
