package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Product;
import com.huotu.huobanmall.repository.ProductRepository;
import com.huotu.huobanmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品Service层
 * Created by shiliting on 2015/8/27.
 */

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;


    @Override
    public List<Product> searchProducts(Merchant merchant, Goods goods) {
        return null;
    }
}
