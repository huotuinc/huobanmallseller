package com.huotu.huobanmall.service.impl;


import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.DeviceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author CJ
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Log log = LogFactory.getLog(DeviceServiceImpl.class);

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private CPARepository cpaRepository;
    @Autowired
    private DeviceUserChangedEventRepository deviceUserChangedEventRepository;


    /**
     * 在新建CPA的过程中 可能会遇上多线程同时要求建立多个同样的CPA；这个时候需要限制这样的请求。
     */
    private final Object lock = new Object();

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private MerchantRepository merchantRepository;


    @Override
    @Transactional
    public Device fetchDevice(String imei, String code, String operation) {
        log.debug("搜寻设备" + imei + " ,cpa:" + code + " ,operation:" + operation);
        Device device = deviceRepository.findOne(imei);
        if (device != null)
            return device;
        synchronized (lock) {
            device = new Device();

            device.setImei(imei);
            device.setSystemDate(new Date());

            CPA cpa = cpaRepository.findByCodeAndOperation(code, operation);
            if (cpa == null) {
                cpa = new CPA(code, operation);
                cpa = cpaRepository.save(cpa);
            }
            device.setCpa(cpa);
            return deviceRepository.save(device);
        }
    }

    @Override
    @Transactional
    public void userChanged(@NotNull Device device, Merchant merchant, Operator operator, String version, String ip) {
        DeviceUserChangedEvent event = new DeviceUserChangedEvent();
        event.setRegister(false);
        if (merchant != null)
            event.setMerchant(merchant);
        if (operator != null)
            event.setOperator(operator);
        event.setDevice(device);
        event.setCreateTime(new Date());
        event.setIp(ip);
        event.setVersionNo(version);
        deviceUserChangedEventRepository.save(event);

        if (merchant != null) {
            merchant.setDevice(device);
            merchantRepository.save(merchant);
        }

        if (operator != null) {
            operator.setDevice(device);
            operatorRepository.save(operator);
        }
    }


}
