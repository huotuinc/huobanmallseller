package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
    List<Order> findByMerchantId(Integer merchantId);
    List<Order> findByMerchantIdAndTimeGreaterThan(Integer merchantId,Date lastTime);

}
