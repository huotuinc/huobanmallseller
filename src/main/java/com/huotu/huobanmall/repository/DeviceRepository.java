package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CJ
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

}
