package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Goods;
import org.springframework.data.domain.Page;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface GoodsService {


    /**
     * Create by shiliting on 2015/8/27
     * 查找商品
     * @param merchant      商户
     * @param status        商品状态
     * @param lastProductId 最后一个显示商品的ID
     * @param pageSize      每页记录数
     * @return              商品信息集合
     */
    Page<Goods> searchProducts(Merchant merchant, Integer status, Integer lastProductId, Integer pageSize);

    /**
     * Create by shiliting on 2015/9/1
     * 统计商品数量
     * @param merchant
     * @return
     */
    Integer countByMerchant(Merchant merchant);
}
