package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDayMember;
import com.huotu.huobanmall.entity.pk.CountDayMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountDayMemberRepository   extends JpaRepository<CountDayMember, CountDayMemberPK>, JpaSpecificationExecutor<CountDayMember> {

  List<CountDayMember> findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(Integer merchantId,Date date);

}
