package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Product;

import java.util.List;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface ProductService {


    /**
     * 查找货品
     * @param merchant      所属商家
     * @param goods         所属商品
     * @return
     */
    List<Product> searchProducts(Merchant merchant,Goods goods);


}
