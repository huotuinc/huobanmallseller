/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.service.SellLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单项
 * Created by shiliting on 2015/8/27.
 */

@Service
public class SellLogServiceImpl implements SellLogService{

    @Autowired
    GoodsRepository goodsRepository;

    @Override
    public List<Object[]> countTopGoodList(Merchant merchant) {
        String hql="SELECT A.title, " +
                " SUM(B.amount) AS Number," +
                " A.id FROM Goods A " +
                " LEFT JOIN OrderItems B ON A.id=B.goodsId" +
                " LEFT JOIN Order C ON B.order=C " +
                " WHERE B.goodsId IS NOT NULL " +
                " AND C.status<>-1 and C.payStatus=1 " +
                " AND A.owner.id=:merchantId GROUP BY A.id,A.title ORDER BY Number DESC";


        List<Object[]> list =(List<Object[]>)goodsRepository.queryHql(hql, query -> {
            query.setParameter("merchantId", merchant.getId());
            query.setMaxResults(10);
        });
//        return sellLogRepository.countTopGoods(merchant.getId(),pageable);
        return list;
    }
}
