/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;

import com.huotu.common.model.AppOS;
import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by lgh on 2015/8/26.
 */

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer>, JpaSpecificationExecutor<Merchant> {

    Merchant findByToken(String token);

    Merchant findByName(String name);

    /**
     * 按照终端类型获取可推送设备列表
     *
     * @param os
     * @return
     */
    @Query("select distinct u.device.pushingToken from Merchant u where u.device.pushingToken is not null and u.device.cpa.os = ?1")
    Set<String> findDeviceByOS(AppOS os);
}
