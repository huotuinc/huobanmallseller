/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.ConfigAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CJ
 */
@Repository
public interface ConfigAppVersionRepository extends JpaRepository<ConfigAppVersion,Long>{

    /**
     * 获取最新版本
     * @return
     */
    ConfigAppVersion findTopByOrderByIdDesc();

    ConfigAppVersion findTopByVersionNo(String versionNo);
}
