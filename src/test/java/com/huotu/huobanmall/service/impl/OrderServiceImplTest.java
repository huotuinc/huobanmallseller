package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.Product;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.repository.ProductRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.OrderService;
import com.huotu.huobanmall.test.TestWebConfig;
import com.huotu.huobanmall.test.WebTestBase;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by shiliting on 2015/8/28.
 */

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
@Transactional
public class OrderServiceImplTest extends WebTestBase {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ProductRepository productRepository;



    @Test
    @Rollback
    public void testSearchOrders() throws Exception {
        Random random=new Random();
//      准备测试环境
        User user=new User();
        user.setId(random.nextInt(200));
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        User userNew=userRepository.save(user);                     //新建一个用户

        Merchant merchant=new Merchant();
        merchant.setId(random.nextInt(200));
        merchant.setName("wy");
        merchant.setEnabled(true);
        merchant.setPassword("654321");
        merchant.setNickName("nicheng");
        Merchant merchantNew=merchantRepository.save(merchant);     //新建一个商家

        Product product=new Product();
        product.setId(random.nextInt(200));
//        product.setMerchantId(merchantNew.getId());
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Product productNew=productRepository.save(product);                            //新建一个商品
        Order order;
        for(int i=0;i<20;i++){
            order=new Order();
            order.setOrderStatus(1);
            order.setMerchantId(merchantNew.getId());
            order.setUserId(userNew.getId());
            order.setTime(new Date());
            order.setProductId(productNew.getId());
            order.setScore(99);
            order.setReceiver("shiliting");
            order.setId(UUID.randomUUID().toString());
            orderRepository.save(order);
        }
//      准备测试环境END
        Assert.assertEquals("是否添加了20条订单",20,orderRepository.count());
        Date date=new Date();
        date.setTime(new Date().getTime()+9999);
//        Page<Order> pages=orderService.searchOrders(merchant.getId(),date,5,1);
//        Assert.assertEquals("测试查询出来的订单总数量是否正确",20,pages.getTotalElements());
//        Assert.assertEquals("测试查询出来的当前页数量是否正确",5,pages.getNumberOfElements());





    }
}