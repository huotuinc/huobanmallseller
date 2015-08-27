package com.huotu.huobanmall.service;

import java.net.URI;

/**
 * @author CJ
 */
public interface SystemService {


    /**
     * 获取系统配置
     * @param key
     * @param defaultValue 如果系统没有设置该值
     * @return
     */
    String systemConfigVor(String key, String defaultValue);

    /**
     * 设置系统配置
     * @param key
     * @param value
     */
    void putSystemConfig(String key, String value);


}
