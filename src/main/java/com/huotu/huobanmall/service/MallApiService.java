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
    String upladPic(Integer customerId, String images, Integer type) throws IOException;

    /**
     * 商城信息修改
     * @param customerId    商家ID
     * @param mallName      商城名称
     * @param mallIntro     商城描述
     * @return
     */
    String updateShopInfo(Integer customerId,String mallName,String mallIntro) throws IOException;

}
