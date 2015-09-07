package com.huotu.huobanmall.controller;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Administrator on 2015/9/5.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class OrderControllerTest extends SpringAppTest {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GoodsRepository productRepository;
    @Autowired
    OrderRepository orderRepository;

    private String mockMerchantName;
    private String mockMerchantPassword;
    private Device device;
    private Merchant mockMerchant;
    private ShopRepository shopRepository;

    @Before
    public void prepareDevice() {
        device = Device.newDevice(DeviceType.Android);
        Random random = new Random();
        mockMerchantName = StringHelper.RandomNo(random, 15);
        mockMerchantPassword = UUID.randomUUID().toString().replace("-", "");
        mockMerchant = generateMerchantWithToken(merchantRepository, shopRepository,mockMerchantName, mockMerchantPassword);
        device.setToken(mockMerchant.getToken());
    }

    @Test
    public void testOrderList() throws Exception {
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
        User user=new User();
        user.setId(random.nextInt(200));
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        user.setMerchant(mockMerchant);
        User userNew=userRepository.save(user);                     //新建一个用户

        Goods product=new Goods();
        product.setId(random.nextInt(200));
        product.setOwner(mockMerchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Goods productNew=productRepository.save(product);                            //新建一个商品

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
            order.setMerchant(mockMerchant);
//            order.setUser(user);
            order.setUserId(user.getId());
            order.setTime(k==1? new Date():(k==2?testSevenDays:oldTime));
//            order.setProductId(productNew.getId());
//            order.setScore(100);
            order.setPrice(25);
            order.setAmount(10);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }
//      准备测试环境END
        mockMvc.perform(
                device.getApi("orderList")
                        .build()
        ).andDo(print());
        Map<String, Object>map= mockMvc.perform(
                device.getApi("orderList")
                        .param("orderStatus", "1")
                        .build()
        ).andReturn().getModelAndView().getModel();
        Page<Order> page=(Page<Order>)map.get("orderList");
        Assert.assertEquals("待付款数量是否一致：",(long)daifukuan,page.getTotalElements());

    }

    @Test
    public void testUserScoreList() throws Exception {
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
        User user=new User();
        user.setId(random.nextInt(200));
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        user.setMerchant(mockMerchant);
        User userNew=userRepository.save(user);                     //新建一个用户

        User user1=new User();
        user1.setId(random.nextInt(200));
        user1.setUsername("wy");
        user1.setPassword("123456");
        user1.setRegTime(new Date());
        user1.setMerchant(mockMerchant);

        Goods product=new Goods();
        product.setId(random.nextInt(200));
        product.setOwner(mockMerchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Goods productNew=productRepository.save(product);                            //新建一个商品

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
            order.setMerchant(mockMerchant);
//            order.setUser(user);
            order.setUserId(user.getId());
            order.setTime(k==1? new Date():(k==2?testSevenDays:oldTime));
//            order.setProductId(productNew.getId());
//            order.setScore(100);
            order.setPrice(25);
            order.setAmount(10);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }
        order=new Order();
        order.setId("10");
        order.setOrderStatus(3);
        order.setMerchant(mockMerchant);
//        order.setUser(user1);
        order.setUserId(user1.getId());
        order.setTime(new Date());
//        order.setProductId(productNew.getId());
//        order.setScore(1000);
        order.setPrice(10);
        order.setAmount(10);
        order.setReceiver("wwyy");
        orderRepository.save(order);
//      准备测试环境END


        Map<String, Object> map=mockMvc.perform(
                device.getApi("userScoreList")
                        .build()
        ).andReturn().getModelAndView().getModel();
        List<Object[]> list=(List<Object[]>)map.get("userScoreList");
        List<User> list1=userRepository.findAll();



        Assert.assertEquals("返利积分用户个数测试:",list1.size(),list.size());


        mockMvc.perform(
                device.getApi("userExpenditureList")
                        .build()
        ).andDo(print());

    }

    @Test
    public void testUserExpenditureList() throws Exception {

    }
}