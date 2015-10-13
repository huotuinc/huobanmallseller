package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.User;

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
     * 按照注册时间计算会员数量
     * @param merchant      会员所属商家
     * @param type          会员类型
     * @param date          会员注册时间
     * @return
     */
    public Integer countUserNumber(Merchant merchant,Integer type,Date date);

    /**
     * Create by shiliting on 2015/9/11
     * 计算今日新增会员数量
     * @param merchant      会员所属商家
     * @return
     */
    public Integer countTodayMember(Merchant merchant);

    /**
     * Create by shiliting on 2015/9/14
     * 计算今日新增小伙伴数量
     * @param merchant
     * @return
     */
    public Integer countTodayPartner(Merchant merchant);

    /**
     * Create by shiliting on 2015/9/19
     * 获取显示的用户名(按照优先级显示)
     * 显示优先级：1.真实姓名，2.微信昵称，3.电话号码，4.用户名
     * 如果User为空则显示""
     * @return
     */
    String getViewUserName(User user);



}
