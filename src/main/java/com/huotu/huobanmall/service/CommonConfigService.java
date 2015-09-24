package com.huotu.huobanmall.service;

/**
 * 通用变量定义
 * Created by lgh on 2015/9/23.
 */
public interface CommonConfigService {

    /**
     * 资源地址
     * @return
     */
    String getResoureServerUrl();

    /**
     * 商城服务api地址
     * @return
     */
    String getMallApiServerUrl();

    /**
     * 获取当前网站的地址
     * @return
     */
    String getWebUrl();

}
