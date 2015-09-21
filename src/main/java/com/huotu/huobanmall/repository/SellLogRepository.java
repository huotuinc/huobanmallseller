package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.SellLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface SellLogRepository extends JpaRepository<SellLog, Integer>, JpaSpecificationExecutor<SellLog> {
    @Query(value = "select s.productId,sum(s.amount) from SellLog s where s.merchantId=?1 group by s.productId order by sum(s.amount) desc")
    Page<Object[]> countTopGoods(Integer productId,Pageable pageable);


}
