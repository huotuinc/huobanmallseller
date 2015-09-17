package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDayPartner;
import com.huotu.huobanmall.entity.CountTodayPartner;
import com.huotu.huobanmall.entity.pk.CountTodayPartnerPK;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountTodayPartnerRepository  extends JpaRepository<CountTodayPartner, CountTodayPartnerPK>
        , JpaSpecificationExecutor<CountTodayPartner>,ClassicsRepository<CountTodayPartner> {
    List<CountTodayPartner> findByMerchantIdAndHourLessThanEqual(Integer merchantId,Integer hour);
    List<CountTodayPartner> findByMerchantId(Integer merchantId);

    CountTodayPartner findByMerchantIdAndHour(Integer merchantId,Integer hour);
}
