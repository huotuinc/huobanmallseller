package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountTodaySales;
import com.huotu.huobanmall.entity.pk.CountTodaySalesPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountTodaySalesRepository  extends JpaRepository<CountTodaySales, CountTodaySalesPK>, JpaSpecificationExecutor<CountTodaySales> {
}
