package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.Rebate;
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
 * Created by shiliting on 2015/9/8.
 */

@Repository
public interface RebateRepository extends JpaRepository<Rebate, Integer>, JpaSpecificationExecutor<Rebate>,ClassicsRepository<Rebate> {

//    @Query(value = "select r  from Rebate r where r.merchant=?1 and r.status=?2 and r.userId in(select u. from User u where )")
//    Page<Rebate> searchRebateList(Merchant merchant,Integer status);

    @Query(value = "select r.userId,sum(r.score) from Rebate r where r.merchant=?1 and r.status=?2 group by r.userId order by sum(r.score) desc ")
    Page<Object[]> findTopScore(Merchant merchant,Integer status,Pageable pageable);

    Page<Rebate> findByMerchantAndStatusLessThanOrderByTimeDesc(Merchant merchant, Integer status, Date time, Pageable pageable);
    List<Rebate> findByMerchantAndOrder(Merchant merchant,Order order);



}
