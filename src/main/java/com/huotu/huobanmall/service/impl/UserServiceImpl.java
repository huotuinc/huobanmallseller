/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.CountTodayMember;
import com.huotu.huobanmall.entity.CountTodayPartner;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.repository.CountTodayMemberRepository;
import com.huotu.huobanmall.repository.CountTodayPartnerRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 会员Service层
 * Created by shiliting on 2015/9/1.
 * Modify by shiliting on 2015/9/6
 */

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    CountTodayMemberRepository countTodayMemberRepository;
    @Autowired
    CountTodayPartnerRepository countTodayPartnerRepository;


    @Override
    public Integer countUserNumber(Merchant merchant, Integer type) {
        return userRepository.findByMerchantAndType(merchant,type).size();
    }

    @Override
    public Integer countUserNumber(Merchant merchant, Integer type, Date date) {
        return userRepository.findByMerchantAndTypeAndRegTimeGreaterThan(merchant,type,date).size();
    }

    @Override
    public Integer countTodayMember(Merchant merchant) {
        List<CountTodayMember> countTodayMembers=countTodayMemberRepository.findByMerchantId(merchant.getId());
        int todayMembers=0;
        for(CountTodayMember m:countTodayMembers){
            todayMembers+=m.getAmount();
        }
        return todayMembers;
    }

    @Override
    public Integer countTodayPartner(Merchant merchant) {
        List<CountTodayPartner> countTodayPartners=countTodayPartnerRepository.findByMerchantId(merchant.getId());
        int todayPartners=0;
        for(CountTodayPartner p:countTodayPartners){
            todayPartners+=p.getAmount();
        }
        return todayPartners;
    }

    @Override
    public String getViewUserName(User user) {
        if(user==null){
            return "";
        }
        String realName=user.getRealName();
        String wxNickName=user.getWxNickName();
        String mobile=user.getMobile();
        String userName=user.getUsername();
        if(StringUtils.isEmpty(realName)){
            if(StringUtils.isEmpty(wxNickName)){
                if(StringUtils.isEmpty(mobile)){
                    return userName;
                }else {
                    return mobile;
                }
            }else {
                return wxNickName;
            }
        }else {
            return realName;
        }
    }
}
