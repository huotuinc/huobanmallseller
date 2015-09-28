package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CJ
 */
@Repository
public interface CPARepository extends JpaRepository<CPA,Long>{


    /**
     * 获取指定编码和平台的渠道信息
     * @param code
     * @param operation
     * @return
     */
    CPA findByCodeAndOperation(String code, String operation);
}
