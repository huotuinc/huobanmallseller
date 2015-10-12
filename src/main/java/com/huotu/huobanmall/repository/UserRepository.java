package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by shiliting on 2015/8/28.
 * Modify by shiliting on 2015/9/6
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    List<User> findByMerchantAndType(Merchant merchant,Integer type);
    List<User> findByMerchantAndTypeAndRegTimeGreaterThan(Merchant merchant,Integer type,Date date);
    Long countByMerchant(Merchant merchant);
    Long countByMerchantAndType(Merchant merchant,Integer status);





}
