package com.huotu.huobanmall.service;

import java.io.IOException;

/**
 * Created by lgh on 2015/9/23.
 */
public interface MallApiService {

    /**
     * 获取商城域名地址
     *
     * @param customerId 商家ID
     * @return
     */
    String getMsiteUrl(Integer customerId) throws IOException;


    /**
     * 上传图片
     *
     * @param customerId 商家Id
     * @param images     图片（base64）
     * @param type       类型 1 logo
     * @return
     */
    String upladPic(Integer customerId, byte[] images, Integer type) throws IOException;

}
