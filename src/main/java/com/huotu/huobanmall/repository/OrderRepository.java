package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import org.luffy.lib.libspring.data.ClassicsRepository;
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

    //TODO返利积分的规则有待确定
    //TODO显示前几条数据

//    @Query(value = "select order.user.username,sum(order.score) from Order order where order.orderStatus<>1 group by order.user.username order by sum(order.score) desc")
//    List<Object[]> countUserScore();
//    @Query(value = "select order.user.username,sum(order.amount*order.price) from Order order where order.orderStatus=3 group by order.user.username order by sum(order.amount*order.price) desc")
//    List<Object[]> countUserExpenditure();
}
