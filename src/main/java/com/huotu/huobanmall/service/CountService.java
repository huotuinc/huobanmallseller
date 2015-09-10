package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.domain.Page;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface CountService {



    Page<Goods> searchProducts(Merchant merchant, Integer status, Integer lastProductId, Integer pageSize);


    Integer countByMerchant(Merchant merchant);
}
