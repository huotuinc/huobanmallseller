package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lgh on 2015/9/1.
 */
public interface OrderItemsService {
    /**
     * Create by shiliting on2015/9/17
     * 统计销售量最高的商品
     * @param merchant      所属的商家
     * @param pageable
     * @return
     */
    Page<Object[]> countTopGoodList(Merchant merchant,Pageable pageable);


}
