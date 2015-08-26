package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/8/26.
 */

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer>, JpaSpecificationExecutor<Merchant> {
}
