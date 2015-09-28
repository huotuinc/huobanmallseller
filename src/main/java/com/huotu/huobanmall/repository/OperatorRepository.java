package com.huotu.huobanmall.repository;

import com.huotu.common.model.AppOS;
import com.huotu.huobanmall.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by lgh on 2015/8/31.
 */

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer>, JpaSpecificationExecutor<Operator> {

    Operator findByToken(String token);
    Operator findByName(String name);

    /**
     * 按照终端类型获取可推送设备列表
     *
     * @param os
     * @return
     */
    @Query("select distinct u.device.pushingToken from Operator u where u.device.pushingToken is not null and u.device.cpa.os = ?1")
    Set<String> findDeviceByOS(AppOS os);
}
