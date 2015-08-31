package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/8/31.
 */

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer>, JpaSpecificationExecutor<Operator> {

    Operator findByToken(String token);
    Operator findByName(String name);
}
