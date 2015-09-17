package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountTodayOrder;
import com.huotu.huobanmall.entity.pk.CountTodayOrderPK;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lgh on 2015/8/26.
 */

@Repository
public interface CountTodayOrderRepository extends JpaRepository<CountTodayOrder, CountTodayOrderPK>, JpaSpecificationExecutor<CountTodayOrder>,ClassicsRepository<CountTodayOrder> {
    List<CountTodayOrder> findByMerchantIdAndHourLessThanEqual(Integer merchantId,Integer hour);
    List<CountTodayOrder> findByMerchantId(Integer merchantId);

    CountTodayOrder findByMerchantIdAndHour(Integer merchantId,Integer hour);
}
