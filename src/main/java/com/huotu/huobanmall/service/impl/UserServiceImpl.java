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
        String realName=user.getRealName();
        String wxNickName=user.getWxNickName();
        String mobile=user.getMobile();
        String userName=user.getUsername();
        if(realName==null||realName==""){
            if(wxNickName==null||wxNickName==""){
                if(mobile==null||mobile==""){
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
