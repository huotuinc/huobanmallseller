package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.DeviceUserChangedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CJ
 */
@Repository
public interface DeviceUserChangedEventRepository extends JpaRepository<DeviceUserChangedEvent, Long> {
}
