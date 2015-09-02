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

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
//      准备测试环境
        Random random=new Random();
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MINUTE,0);
        //今天
        Date today=date.getTime();
        date.set(Calendar.DATE,-5);
        //近七日
        Date sevenDays=date.getTime();
        //更久
        Date testSevenDays=new Date(date.getTime().getTime()+3000000);
        Date oldTime=new Date(date.getTime().getTime()-3000000);
        int daifukuan=0;
        int daishouhuo=0;
        int wancheng=0;
        Integer todayNum=0;
        Integer sevendays=0;
        Integer oldDays=0;
        Merchant merchant=new Merchant();
        merchant.setId(random.nextInt(200));
        merchant.setName("wy");
        merchant.setEnabled(true);
        merchant.setPassword("654321");
        merchant.setNickName("nicheng");
        Merchant merchantNew=merchantRepository.save(merchant);     //新建一个商家

        User user=new User();
        user.setId(random.nextInt(200));
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        user.setMerchant(merchant);
        User userNew=userRepository.save(user);                     //新建一个用户

        Product product=new Product();
        product.setId(random.nextInt(200));
        product.setOwner(merchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Product productNew=productRepository.save(product);                            //新建一个商品

        Order order;
        for(int i=0;i<20;i++){
            order=new Order();
            order.setId(String.valueOf(100-i));
            int k=random.nextInt(3)+1;
            switch (k){
                case 1:
                    daifukuan++;
                    todayNum++;
                    break;
                case 2:
                    daishouhuo++;
                    sevendays++;
                    break;
                case 3:
                    wancheng++;
                    oldDays++;
            }
            order.setOrderStatus(k);
            order.setMerchant(merchant);
            order.setUser(user);
            order.setTime(k==1? new Date():(k==2?testSevenDays:oldTime));
            order.setProductId(productNew.getId());
            order.setScore(99);
            order.setPrice(25);
            order.setAmount(10);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }
//      准备测试环境END
        Assert.assertEquals("是否添加了20条订单",20,orderRepository.count());
        Page<Order> pages=orderService.searchOrders(merchantNew.getId(),"999",6,null);
        Assert.assertEquals("测试查询出来的的订单总数量是否正确",daifukuan+daishouhuo+wancheng,pages.getTotalElements());
        Assert.assertEquals("测试查询出来的当前页的数量是否正确", 6, pages.getNumberOfElements());
        pages=orderService.searchOrders(merchantNew.getId(),null,5,null);
        Assert.assertEquals("测试查询出来的的订单总数量是否正确",daifukuan+daishouhuo+wancheng,pages.getTotalElements());
        Assert.assertEquals("测试查询出来的当前页的数量是否正确", 5, pages.getNumberOfElements());
        pages=orderService.searchOrders(merchantNew.getId(),null,5,1);
        Assert.assertEquals("测试查询出来的的待付款的订单总数量是否正确",daifukuan,pages.getTotalElements());
        Assert.assertEquals("测试查询出来的当前页的代付款数量是否正确", daifukuan > 5 ? 5 : daifukuan, pages.getNumberOfElements());
        pages=orderService.searchOrders(merchantNew.getId(),"9999",5,1);
        Assert.assertEquals("测试查询出来的待付款的订单总数量是否正确",daifukuan,pages.getTotalElements());
        Assert.assertEquals("测试查询出来的当前页的代付款数量是否正确", daifukuan > 5 ? 5 : daifukuan, pages.getNumberOfElements());
        pages=orderService.searchOrders(merchantNew.getId(),"9999",4,2);
        Assert.assertEquals("测试查询出来的待收货的订单总数量是否正确",daishouhuo,pages.getTotalElements());
        Assert.assertEquals("测试查询出来的当前页的待收货数量是否正确",daishouhuo>4?4:daishouhuo,pages.getNumberOfElements());
        pages=orderService.searchOrders(merchantNew.getId(),"9999",3,3);
        Assert.assertEquals("测试查询出来的完成的订单总数量是否正确",wancheng,pages.getTotalElements());
        Assert.assertEquals("测试查询出来的当前页的完成的数量是否正确",wancheng>3?3:wancheng,pages.getNumberOfElements());

        Integer expected=20;
        sevendays=sevendays+todayNum;
        Integer orders=orderService.countOrderQuantity(merchantNew);
        Assert.assertEquals("测试订单总数量",expected,orders);

        orders=orderService.countOrderQuantity(merchantNew,today);
//        orders=orderRepository.countByWeekOrMonth(today,merchantNew);
        Assert.assertEquals("测试今日订单数量",todayNum,orders);

        orders=orderService.countOrderQuantity(merchantNew,sevenDays);
//        orders=orderRepository.countByWeekOrMonth(sevenDays,merchantNew);
        Assert.assertEquals("测试近七日订单数量",sevendays,orders);





    }

    @Test
    public void testCountOrderQuantity() throws Exception {
        //      准备测试环境
        Random random=new Random();
        Merchant merchant=new Merchant();
        merchant.setId(random.nextInt(200));
        merchant.setName("wy");
        merchant.setEnabled(true);
        merchant.setPassword("654321");
        merchant.setNickName("nicheng");
        Merchant merchantNew=merchantRepository.save(merchant);     //新建一个商家

        User user=new User();
        user.setId(random.nextInt(200));
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        user.setMerchant(merchant);
        User userNew=userRepository.save(user);                     //新建一个用户

        Product product=new Product();
        product.setId(random.nextInt(200));
        product.setOwner(merchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Product productNew=productRepository.save(product);                            //新建一个商品

        Order order;
        for(int i=0;i<10;i++){
            order=new Order();
            order.setId(String.valueOf(100-i));
            order.setOrderStatus(1);
            order.setMerchant(merchant);
            order.setUser(user);
            order.setTime(new Date());
            order.setProductId(productNew.getId());
            order.setScore(1);
            order.setPrice(10);
            order.setAmount(2);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }

        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MINUTE,0);
        //今天
        Date today=date.getTime();
        date.set(Calendar.DATE,-5);
        //近七日
        Date sevenDays=new Date(date.getTime().getTime()+3);
        //更久
        Date oldTime=new Date(date.getTime().getTime()-3);
        for(int i=0;i<20;i++){
            order=new Order();
            order.setId(String.valueOf(100-i));
            order.setOrderStatus(1);
            order.setMerchant(merchant);
            order.setUser(user);
            order.setTime(sevenDays);
            order.setProductId(productNew.getId());
            order.setScore(1);
            order.setPrice(10);
            order.setAmount(2);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }
        for(int i=0;i<5;i++){
            order=new Order();
            order.setId(String.valueOf(100-i));
            order.setOrderStatus(1);
            order.setMerchant(merchant);
            order.setUser(user);
            order.setTime(oldTime);
            order.setProductId(productNew.getId());
            order.setScore(1);
            order.setPrice(10);
            order.setAmount(2);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }
//      准备测试环境END
        Integer orders=orderService.countOrderQuantity(merchantNew);
        Integer expected=35;
        Assert.assertEquals("测试订单数量",35,orderRepository.findAll().size());

//        Assert.assertEquals("测试订单数量",expected,orders);


    }



    @Test
    public void testCountSale() throws Exception {

    }

}