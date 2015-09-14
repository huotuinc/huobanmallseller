package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.UserChangeLog;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/9/14.
 */

@Repository
public interface UserChangeLogRepository extends JpaRepository<UserChangeLog, Integer>
        , JpaSpecificationExecutor<UserChangeLog>, ClassicsRepository<UserChangeLog> {
}
