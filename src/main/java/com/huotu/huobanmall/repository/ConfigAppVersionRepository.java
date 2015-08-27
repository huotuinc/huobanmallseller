package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.ConfigAppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CJ
 */
@Repository
public interface ConfigAppVersionRepository extends JpaRepository<ConfigAppVersion,Long>{

    /**
     * 获取最新版本
     * @return
     */
    ConfigAppVersion findTopByOrderByIdDesc();

    ConfigAppVersion findTopByVersionNo(String versionNo);
}
