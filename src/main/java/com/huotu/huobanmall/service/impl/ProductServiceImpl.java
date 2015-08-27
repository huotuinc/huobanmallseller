package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Product;
import com.huotu.huobanmall.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * 商品Service层
 * Created by shiliting on 2015/8/27.
 */

@Service
public class ProductServiceImpl implements ProductService{


    @Override
    public Page<Product> findAll() {
        return null;
    }

    @Override
    public Product findOneById(Integer productId) {
        return null;
    }

    @Override
    public Product saveOne(Product product) {
        return null;
    }

}
