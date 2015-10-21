/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.SellLog;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface SellLogRepository extends JpaRepository<SellLog, Integer>, JpaSpecificationExecutor<SellLog>,ClassicsRepository<SellLog> {
//    @Query(value = "select s.productId,sum(s.amount) from SellLog s where s.merchantId=?1 group by s.productId order by sum(s.amount) desc")
//    @Query(value = "SELECT A.title,SUM(B.amount) AS Number,SUM(B.price*B.amount) AS Price, A.id FROM Goods A LEFT JOIN OrderItems B ON A.id=B.id LEFT JOIN Order C ON B.order.id=C.id WHERE B.goodsId IS NOT NULL AND C.Status<>-1 and C.payStatus=1 AND A.merchant.id=?1 GROUP BY A.id,A.title ORDER BY Number DESC")
//    Page<Object[]> countTopGoods(Integer productId,Pageable pageable);


}
