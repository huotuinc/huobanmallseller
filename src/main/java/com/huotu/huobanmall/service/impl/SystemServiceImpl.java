package com.huotu.huobanmall.service.impl;


import com.huotu.huobanmall.entity.SystemConfig;
import com.huotu.huobanmall.repository.SystemConfigRepository;
import com.huotu.huobanmall.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author CJ
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;
    @Autowired
    private Environment env;
    private URI defaultUserLogo;

    @PostConstruct
    public void init() throws URISyntaxException {

    }


    @Override
    @Deprecated
    public String systemConfigVor(String key, String defaultValue) {
        try {
            return systemConfigRepository.getOne(key).getValueForCode();
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    @Override
    @Deprecated
    public void putSystemConfig(String key, String value) {
        SystemConfig systemConfig;
        try {
            systemConfig = systemConfigRepository.getOne(key);
        } catch (Exception e) {
            systemConfig = new SystemConfig();
            systemConfig.setCode(key);
        }
        systemConfig.setValueForCode(value);
        systemConfigRepository.saveAndFlush(systemConfig);
    }


}
