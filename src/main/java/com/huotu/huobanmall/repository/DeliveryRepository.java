package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Delivery;
import com.huotu.huobanmall.entity.Order;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface DeliveryRepository extends JpaRepository<Delivery, Integer>, JpaSpecificationExecutor<Delivery>, ClassicsRepository<Delivery> {
    List<Delivery> findByOrder(Order order);

}
