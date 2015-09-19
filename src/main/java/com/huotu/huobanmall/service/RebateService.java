package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import org.springframework.data.domain.Page;

/**
 * Created by lgh on 2015/9/1.
 */
public interface RebateService {

    /**
     * 最高返利会员
     * @param merchant      所属商家
     * @param status        状态
     * @return
     */
    Page<Rebate> searchUserScore(Merchant merchant,Integer status);

}
