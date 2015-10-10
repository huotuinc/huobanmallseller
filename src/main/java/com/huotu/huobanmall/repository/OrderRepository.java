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

import java.util.Collection;
import java.util.List;


/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order>,ClassicsRepository<Order> {


    List<Order> findByMerchant(Merchant merchant);
    @Query("select o from Order o where o.mainOrderNo in ?1 order by o.time desc")
    List<Order> findByMainOrderNo(Collection<String> mainOrderNo);
    Page<Order> findByMerchantAndPayStatusOrderByPriceDesc(Merchant merchant,Integer payStatus,Pageable pageable);

    @Query(value = "select o.userId,sum(o.price),count(o) from Order o where o.payStatus=1 and o.merchant=?1 group by o.userId order by sum(o.price) desc")
    Page<Object[]> countUserExpenditure(Merchant merchant,Pageable pageable);
}
