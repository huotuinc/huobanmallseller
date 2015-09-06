package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员Service层
 * Created by shiliting on 2015/9/1.
 * Modify by shiliting on 2015/9/6
 */

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;


    @Override
    public Integer countUserNumber(Merchant merchant, Integer type) {
        return userRepository.findByMerchantAndType(merchant,type).size();
    }

    @Override
    public Integer countUserNumber(Merchant merchant, Integer type, Date date) {
        return userRepository.findByMerchantAndTypeAndRegTimeGreaterThan(merchant,type,date).size();
    }
}
