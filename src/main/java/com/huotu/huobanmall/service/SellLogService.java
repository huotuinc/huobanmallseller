/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lgh on 2015/9/1.
 */
public interface SellLogService {
    /**
     * Create by shiliting on2015/9/21
     * 统计销售量最高的商品
     * @param merchant      所属的商家
     * @return
     */
    List<Object[]> countTopGoodList(Merchant merchant);






}
