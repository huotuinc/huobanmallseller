/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by shiliting on 2015/8/28.
 * Modify by shiliting on 2015/9/6
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    List<User> findByMerchantAndType(Merchant merchant,Integer type);
    List<User> findByMerchantAndTypeAndRegTimeGreaterThan(Merchant merchant,Integer type,Date date);
    Long countByMerchantAndTypeNot(Merchant merchant,Integer type);
    Long countByMerchantAndType(Merchant merchant,Integer status);





}
