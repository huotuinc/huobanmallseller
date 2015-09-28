package com.huotu.huobanmall.repository;

import com.huotu.common.model.AppOS;
import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Created by lgh on 2015/8/26.
 */

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer>, JpaSpecificationExecutor<Merchant> {

    Merchant findByToken(String token);

    Merchant findByName(String name);

    /**
     * 按照终端类型获取可推送设备列表
     *
     * @param os
     * @return
     */
    @Query("select distinct u.device.pushingToken from Merchant u where u.device.pushingToken is not null and u.device.cpa.os = ?1")
    Set<String> findDeviceByOS(AppOS os);
}
