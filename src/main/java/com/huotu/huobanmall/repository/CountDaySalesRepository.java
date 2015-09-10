package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDaySales;
import com.huotu.huobanmall.entity.pk.CountDaySalesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountDaySalesRepository extends JpaRepository<CountDaySales, CountDaySalesPK>, JpaSpecificationExecutor<CountDaySales> {

    List<CountDaySales> findByMerchantIdAndDateGreaterThanEqualOrderByDate(Integer merchantId, Date date);
}
