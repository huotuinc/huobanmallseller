/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDayMember;

import com.huotu.huobanmall.entity.pk.CountDayMemberPK;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountDayMemberRepository   extends JpaRepository<CountDayMember, CountDayMemberPK>, JpaSpecificationExecutor<CountDayMember>,ClassicsRepository<CountDayMember> {

  List<CountDayMember> findByMerchantIdAndDateGreaterThanEqualOrderByDate(Integer merchantId, Date date);
  List<CountDayMember> findByMerchantId(Integer merchantId);

  CountDayMember findByMerchantIdAndDate(Integer merchantId, Date date);
}
