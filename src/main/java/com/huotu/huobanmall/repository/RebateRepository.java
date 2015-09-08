package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Created by shiliting on 2015/9/8.
 */

@Repository
public interface RebateRepository extends JpaRepository<Rebate, Integer>, JpaSpecificationExecutor<Rebate>,ClassicsRepository<Rebate> {
    Page<Rebate> findByMerchantAndStatusOrderByScoreDesc(Merchant merchant,Integer status,Pageable pageable);



}
