package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountTodaySales;
import com.huotu.huobanmall.entity.pk.CountTodaySalesPK;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountTodaySalesRepository extends JpaRepository<CountTodaySales, CountTodaySalesPK>, JpaSpecificationExecutor<CountTodaySales>
        , ClassicsRepository<CountTodaySales> {

    List<CountTodaySales> findAllByMerchantIdOrderByHour(Integer merchantId);

    List<CountTodaySales> findByMerchantId(Integer merchantId);

    CountTodaySales findByMerchantIdAndHour(Integer merchantId, Integer hour);

}
