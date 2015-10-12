package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by lgh on 2015/9/1.
 */
public interface SellLogService {
    /**
     * Create by shiliting on2015/9/21
     * 统计销售量最高的商品
     * @param merchant      所属的商家
     * @return
     */
    List<Object[]> countTopGoodList(Merchant merchant);






}
