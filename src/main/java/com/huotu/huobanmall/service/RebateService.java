package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import org.springframework.data.domain.Page;

import java.util.Date;

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
    Page<Object[]> searchTopScore(Merchant merchant, Integer status);

    /**
     * 会员返利列表
     * @param merchant      所属商家
     * @param status        积分状态
     * @param lastId          最后一条显示的id
     * @return
     */
    Page<Rebate> searchUserScore(Merchant merchant, Integer status,Integer lastId);

    /**
     * 返回积分状态
     * @param status        状态
     * @return
     */
    String getScoreStatus(Integer status);





}
