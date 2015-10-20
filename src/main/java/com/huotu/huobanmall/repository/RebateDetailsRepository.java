package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.RebateDetails;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Created by shiliting on 2015/9/8.
 */

@Repository
public interface RebateDetailsRepository extends JpaRepository<RebateDetails, Integer>, JpaSpecificationExecutor<RebateDetails>,ClassicsRepository<RebateDetails> {




}
