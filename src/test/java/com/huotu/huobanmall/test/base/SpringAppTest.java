/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.test.base;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.repository.UserRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * Created by lgh on 2015/8/28.
 */

public class SpringAppTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void createMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    protected Merchant generateMerchantWithToken(MerchantRepository merchantRepository, ShopRepository shopRepository, String mockMerchantPassword) {
        Random random = new Random();
        return generateMerchantWithToken(merchantRepository, shopRepository, StringHelper.RandomNo(random, 15), mockMerchantPassword);
    }

    protected Merchant generateMerchantWithToken(MerchantRepository merchantRepository, ShopRepository shopRepository, String mockMerchantName, String mockMerchantPassword) {
        String token = createToken();
        Merchant merchant = new Merchant();
        merchant.setPassword(mockMerchantPassword);
        merchant.setEnableBillNotice(true);
        merchant.setToken(token);
        merchant.setMobile("");
//        merchant.setEnabled(true);
        merchant.setEnablePartnerNotice(true);
        merchant.setName(mockMerchantName);
        merchant.setNickName("伙伴商城abc");
        merchant = merchantRepository.saveAndFlush(merchant);

        Shop shop = new Shop();
        shop.setTitle("伙伴商城");
        shop.setDiscription("");
        shop.setLogo("");
        shop.setMerchant(merchant);
        shopRepository.saveAndFlush(shop);

        return merchant;
    }

    protected User generateUser(UserRepository userRepository, Merchant merchant) {
        return generateUser(userRepository, merchant, UUID.randomUUID().toString());
    }

    protected User generateUser(UserRepository userRepository, Merchant merchant, String userName) {
        User user = new User();
        user.setRegTime(new Date());
        user.setPassword(UUID.randomUUID().toString());
        user.setUsername(userName);
        user.setType(0);
        user.setMerchant(merchant);
        return userRepository.saveAndFlush(user);
    }

    protected Order generateOrder(OrderRepository orderRepository, Merchant merchant, User user) {
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        Order order = new Order();
        order.setId(createOrderNo(random));
        order.setTime(calendar.getTime());
        order.setPayTime(calendar.getTime());
        order.setUserId(user.getId());
        order.setPrice(100);
        order.setTitle("title");
        order.setAmount(50);
        order.setUserType(1);
        order.setIsTax(1);
        order.setIsProtect(1);
        return orderRepository.saveAndFlush(order);
    }

    protected String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    protected String createOrderNo(Random random) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime()) + random.nextInt(1000000);
    }

    protected String createDeliveryNo(Random random) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime()) + random.nextInt(1000000);
    }
}
