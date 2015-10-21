/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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


}
