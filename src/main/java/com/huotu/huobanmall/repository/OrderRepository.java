package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order>,ClassicsRepository<Order> {

    List<Order> findByMerchant(Merchant merchant);
    List<Order> findByMerchantAndTimeGreaterThan(Merchant merchant,Date lastTime);

    @Query(value = "select count(order) from Order order where order.time>=?1")
    Integer countByWeekOrMonth(Date startDate);

    //TODO 返利积分的规则有待确定
    //TODO 显示前几条数据

//    @Query(value = "select order.user.username,sum(order.score) from Order order where order.orderStatus<>1 group by order.user.username order by sum(order.score) desc")
//    List<Object[]> countUserScore();
    @Query(value = "select o.userId,sum(o.price) from Order o where o.orderStatus=1 and o.merchant=?1 group by o.userId order by sum(o.price) desc")
    Page<Object[]> countUserExpenditure(Merchant merchant,Pageable pageable);
}
