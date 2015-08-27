package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 不推荐直接使用
 * @author CJ
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String> {

    @Query(value="select sc.valueForCode from SystemConfig sc where sc.code = ?1")
    String getValueForCodeByCode(String code);
}
