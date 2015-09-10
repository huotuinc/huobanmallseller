package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountTodayPartner;
import com.huotu.huobanmall.entity.pk.CountTodayPartnerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountTodayPartnerRepository  extends JpaRepository<CountTodayPartner, CountTodayPartnerPK>, JpaSpecificationExecutor<CountTodayPartner> {
    List<CountTodayPartner> findByMerchantIdAndHourLessThanEqual(Integer merchantId,Integer hour);
}
