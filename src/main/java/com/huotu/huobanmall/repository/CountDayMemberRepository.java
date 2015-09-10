package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountDayMember;
import com.huotu.huobanmall.entity.pk.CountDayMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountDayMemberRepository   extends JpaRepository<CountDayMember, CountDayMemberPK>, JpaSpecificationExecutor<CountDayMember> {
}
