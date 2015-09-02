package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Order;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order>,ClassicsRepository<Order> {

    @Query(value = "select count(*) from Order order where order.time>=?1")
    Integer countByWeekOrMonth(Date startDate);
}
