/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Product;

import java.util.List;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface ProductService {


    /**
     * 查找货品
     * @param merchant      所属商家
     * @param goods         所属商品
     * @return
     */
    List<Product> searchProducts(Merchant merchant,Goods goods);


}
