package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.CountTodayMember;
import com.huotu.huobanmall.entity.pk.CountTodayMemberPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by lgh on 2015/9/10.
 */
public interface CountTodayMemberRepository extends JpaSpecificationExecutor<CountTodayMember>, JpaRepository<CountTodayMember, CountTodayMemberPK> {
}
