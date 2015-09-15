package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.OrderItems;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer>, JpaSpecificationExecutor<OrderItems>,ClassicsRepository<OrderItems> {
                List<OrderItems> findByOrder(Order order);
}
