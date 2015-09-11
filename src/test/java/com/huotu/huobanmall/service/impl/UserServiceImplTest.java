package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.UserService;
import com.huotu.huobanmall.test.TestWebConfig;
import com.huotu.huobanmall.test.WebTestBase;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2015/9/11.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
@Transactional
public class UserServiceImplTest extends WebTestBase {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MerchantRepository merchantRepository;


    @Test
    public void testCountUserNumber() throws Exception {
        //准备测试环境
        Random random=new Random();
        Merchant merchant=new Merchant();
        merchant.setId(100);
        Merchant mockMerchant=merchantRepository.save(merchant);
        User user;
        for(int i=0;i<14;i++){
            user=new User();
            user.setMerchant(mockMerchant);
            user.setType(0);
            user.setUsername("11");
            user.setPassword("22");
            user.setRegTime(new Date());
            userRepository.save(user);
        }
        for(int i=0;i<4;i++){
            user=new User();
            user.setMerchant(mockMerchant);
            user.setType(1);
            user.setUsername("11");
            user.setPassword("22");
            user.setRegTime(new Date());
            userRepository.save(user);
        }
        //准备测试环境END
        Integer member=userService.countUserNumber(mockMerchant,0);
        Integer partner=userService.countUserNumber(mockMerchant,1);
        Assert.assertEquals("测试会员数量：",14,(int)member);
        Assert.assertEquals("测试小伙伴数量：",4,(int)partner);


    }
}