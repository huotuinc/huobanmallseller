package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shiliting on 2015/8/27.
 */

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods>,ClassicsRepository<Goods> {

    Long countByOwnerAndDisabledAndStatus(Merchant merchant, Integer disable,Integer status);
}
