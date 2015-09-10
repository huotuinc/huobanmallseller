package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDayOrder;
import com.huotu.huobanmall.entity.pk.CountDayOrderPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountDayOrderRepository extends JpaRepository<CountDayOrder, CountDayOrderPK>, JpaSpecificationExecutor<CountDayOrder> {

    List<CountDayOrder> findByMerchantIdAndDateGreaterThanEqualOrderByDate(Integer merchantId, Date date);

}
