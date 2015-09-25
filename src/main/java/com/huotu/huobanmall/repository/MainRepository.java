package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.MainOrder;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Created by shiliting on 2015/9/25.
 */

@Repository
public interface MainRepository extends JpaRepository<MainOrder, String>, JpaSpecificationExecutor<MainOrder>,ClassicsRepository<MainOrder> {
}
