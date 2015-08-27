package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Product;
import org.springframework.data.domain.Page;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface ProductService {
    /**
     * Create by shiliting on 2015/8/27
     * 查找所有的商品，并以分页显示
     * @return   多个商品信息
     */
    Page<Product> findAll();


    /**
     * Create by shiliting on 2015/8/27
     * 根据商品ID查找一条记录
     * @return  一条商品信息
     */
    Product findOneById(Integer productId);


    /**
     * Create by shiliting on 2015/8/27
     * @param product  需要保存的商品对象
     * @return   返回保存的商品信息
     */
    Product saveOne(Product product);






}
