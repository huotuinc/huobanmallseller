package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDayPartner;
import com.huotu.huobanmall.entity.pk.CountDayPartnerPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountDayPartnerRepository extends JpaRepository<CountDayPartner, CountDayPartnerPK>, JpaSpecificationExecutor<CountDayPartner> {

    List<CountDayPartner> findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(Integer merchantId,Date date);
}
