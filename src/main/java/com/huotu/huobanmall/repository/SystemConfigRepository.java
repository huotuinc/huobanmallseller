/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 不推荐直接使用
 * @author CJ
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String> {

    @Query(value="select sc.valueForCode from SystemConfig sc where sc.code = ?1")
    String getValueForCodeByCode(String code);
}
