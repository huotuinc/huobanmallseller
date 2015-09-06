package com.huotu.huobanmall.bootconfig;

import com.huotu.huobanmall.model.app.AppGlobalModel;
import org.luffy.lib.libspring.data.ClassicsRepositoryFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Administrator on 2015/8/19.
 */
@Configuration
@ComponentScan(value = {"com.huotu.huobanmall.service.impl", "com.huotu.common.service.impl"})
@ImportResource(value = {"classpath:spring-jpa.xml"})
@EnableJpaRepositories(value = {"com.huotu.huobanmall.repository"}, repositoryFactoryBeanClass = ClassicsRepositoryFactoryBean.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RootConfig {

    @Bean
    public AppGlobalModel appGlobalModel() {
        return new AppGlobalModel();
    }

}
