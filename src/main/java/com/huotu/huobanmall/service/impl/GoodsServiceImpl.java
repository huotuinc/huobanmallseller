package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 商品Service层
 * Created by shiliting on 2015/8/27.
 */

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsRepository productRepository;


    @Override
    public Page<Goods> searchProducts(Integer merchantId, Integer status, Integer lastProductId, Integer pageSize) {
        return  productRepository.findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(status==null&&lastProductId==null){
                    return cb.equal(root.get("merchantId").as(Integer.class),merchantId);
                }
                else if(status==null){
                    return cb.and(
                            cb.equal(root.get("merchantId").as(Integer.class),merchantId),
                            cb.greaterThan(root.get("id").as(Integer.class), lastProductId)
                    );
                }else if(lastProductId==null){
                    return cb.and(
                            cb.equal(root.get("merchantId").as(Integer.class),merchantId),
                            cb.equal(root.get("status").as(Integer.class),status)
                    );
                }else{
                    return cb.and(
                            cb.equal(root.get("merchantId").as(Integer.class),merchantId),
                            cb.equal(root.get("status").as(Integer.class),status),
                            cb.greaterThan(root.get("id").as(Integer.class),lastProductId)
                    );
                }

            }
        },new PageRequest(0, pageSize,new Sort(Sort.Direction.DESC,"id")));
    }

    @Override
    public Integer countByMerchant(Merchant merchant) {
        return productRepository.findByOwner(merchant).size();
    }


}
