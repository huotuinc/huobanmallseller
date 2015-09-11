package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;

import java.util.Date;

/**
 * Created by Shiliting on 2015/9/1.
 */
public interface UserService {
    /**
     * Create by shiliting on 2015/9/6
     * 计算会员数量
     * @param merchant      会员所属商家
     * @param type          会员类型
     * @return              会员数量
     */
    public Integer countUserNumber(Merchant merchant,Integer type);

    /**
     * Create by shiliting on 2015/9/6
     * @param merchant      会员所属商家
     * @param type          会员类型
     * @param date          会员注册时间
     * @return
     */
    public Integer countUserNumber(Merchant merchant,Integer type,Date date);

    /**
     * Create by shiliting on 2015/9/11
     * @param merchant      会员所属商家
     * @return
     */
    public Long countAllMember(Merchant merchant);



}
