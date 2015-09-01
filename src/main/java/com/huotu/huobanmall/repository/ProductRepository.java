package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findByOwner(Merchant merchant);
}
