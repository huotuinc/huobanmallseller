package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import org.springframework.data.domain.Page;

/**
 * Created by lgh on 2015/9/1.
 */
public interface RebateService {

    Page<Rebate> showTopScore(Merchant merchant,Integer status);

}
