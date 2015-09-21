package com.huotu.huobanmall.controller;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.entity.*;
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
@Deprecated
public class OrderControllerTest extends SpringAppTest {
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GoodsRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RebateRepository rebateRepository;

    private String mockMerchantName;
    private String mockMerchantPassword;
    private Device device;
    private Merchant mockMerchant;
    @Autowired
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
    public  void  t() throws  Exception
    {

    }

//    @Test
//    public void testOrderList() throws Exception {
//        //      准备测试环境
//        Random random=new Random();
//        Calendar date = Calendar.getInstance();
//        date.setTime(new Date());
//        date.set(Calendar.HOUR_OF_DAY, 0);
//        date.set(Calendar.SECOND,0);
//        date.set(Calendar.MINUTE,0);
//        //今天
//        Date today=date.getTime();
//        date.set(Calendar.DATE,-5);
//        //近七日
//        Date sevenDays=date.getTime();
//        //更久
//        Date testSevenDays=new Date(date.getTime().getTime()+3000000);
//        Date oldTime=new Date(date.getTime().getTime()-3000000);
//        int daifukuan=0;
//        int daishouhuo=0;
//        int wancheng=0;
//        Integer todayNum=0;
//        Integer sevendays=0;
//        Integer oldDays=0;
//        User user=new User();
//        user.setId(random.nextInt(200));
//        user.setUsername("shiliting");
//        user.setPassword("123456");
//        user.setRegTime(new Date());
//        user.setMerchant(mockMerchant);
//        User userNew=userRepository.save(user);                     //新建一个用户
//
//        Goods product=new Goods();
//        product.setId(random.nextInt(200));
//        product.setOwner(mockMerchant);
//        product.setPrice(100);
//        product.setStatus(1);
//        product.setStock(1000);
//        Goods productNew=productRepository.save(product);                            //新建一个商品
//
//        Order order;
//        for(int i=0;i<20;i++){
//            order=new Order();
//            order.setId(String.valueOf(100-i));
//            int k=random.nextInt(3)+1;
//            switch (k){
//                case 1:
//                    daifukuan++;
//                    todayNum++;
//                    break;
//                case 2:
//                    daishouhuo++;
//                    sevendays++;
//                    break;
//                case 3:
//                    wancheng++;
//                    oldDays++;
//            }
//            order.setPayStatus(k);
//            order.setMerchant(mockMerchant);
////            order.setUser(user);
//            order.setUserId(user.getId());
//            order.setTime(k==1? new Date():(k==2?testSevenDays:oldTime));
////            order.setProductId(productNew.getId());
////            order.setScore(100);
//            order.setPrice(25);
//            order.setAmount(10);
//            order.setReceiver("史利挺");
//            orderRepository.save(order);
//        }
////      准备测试环境END
//        mockMvc.perform(
//                device.getApi("orderList")
//                        .build()
//        ).andDo(print());
//        Map<String, Object>map= mockMvc.perform(
//                device.getApi("orderList")
//                        .param("orderStatus", "1")
//                        .build()
//        ).andReturn().getModelAndView().getModel();
//        Page<Order> page=(Page<Order>)map.get("orderList");
//        Assert.assertEquals("待付款数量是否一致：",(long)daifukuan,page.getTotalElements());
//
//    }
//
//    @Test
//    public void testUserScoreList() throws Exception {  //TODO 该单元测试需要修改
//        //      准备测试环境
//        Random random=new Random();
//        Calendar date = Calendar.getInstance();
//        date.setTime(new Date());
//        date.set(Calendar.HOUR_OF_DAY, 0);
//        date.set(Calendar.SECOND,0);
//        date.set(Calendar.MINUTE,0);
//        //今天
//        Date today=date.getTime();
//        date.set(Calendar.DATE,-5);
//        //近七日
//        Date sevenDays=date.getTime();
//        //更久
//        Date testSevenDays=new Date(date.getTime().getTime()+3000000);
//        Date oldTime=new Date(date.getTime().getTime()-3000000);
//        int daifukuan=0;
//        int daishouhuo=0;
//        int wancheng=0;
//        Integer todayNum=0;
//        Integer sevendays=0;
//        Integer oldDays=0;
//        User user=new User();
//        user.setId(random.nextInt(200));
//        user.setUsername("shiliting");
//        user.setPassword("123456");
//        user.setRegTime(new Date());
//        user.setMerchant(mockMerchant);
//        User userNew=userRepository.save(user);                     //新建一个用户
//
//        User user1=new User();
//        user1.setId(random.nextInt(200));
//        user1.setUsername("wy");
//        user1.setPassword("123456");
//        user1.setRegTime(new Date());
//        user1.setMerchant(mockMerchant);
//
//        Goods product=new Goods();
//        product.setId(random.nextInt(200));
//        product.setOwner(mockMerchant);
//        product.setPrice(100);
//        product.setStatus(1);
//        product.setStock(1000);
//        Goods productNew=productRepository.save(product);                            //新建一个商品
//
//        Order order;
//        for(int i=0;i<20;i++){
//            order=new Order();
//            order.setId(String.valueOf(100-i));
//            int k=random.nextInt(3)+1;
//            switch (k){
//                case 1:
//                    daifukuan++;
//                    todayNum++;
//                    break;
//                case 2:
//                    daishouhuo++;
//                    sevendays++;
//                    break;
//                case 3:
//                    wancheng++;
//                    oldDays++;
//            }
//            order.setPayStatus(k);
//            order.setMerchant(mockMerchant);
////            order.setUser(user);
//            order.setUserId(user.getId());
//            order.setTime(k==1? new Date():(k==2?testSevenDays:oldTime));
////            order.setProductId(productNew.getId());
////            order.setScore(100);
//            order.setPrice(5000);
//            order.setAmount(10);
//            order.setReceiver("史利挺");
//            orderRepository.save(order);
//        }
//
//        Rebate rebate=new Rebate();
//        rebate.setUserId(user.getId());
//        rebate.setMerchant(mockMerchant);
//        rebate.setId(1);
//        rebate.setScore(rebate.getScore()==null?99990:rebate.getScore()+100);
//        rebate.setStatus(1);
//        rebate.setTime(new Date());
//        rebateRepository.save(rebate);
//
//
//        order=new Order();
//        order.setId("10");
//        order.setPayStatus(1);
//        order.setMerchant(mockMerchant);
//        order.setUserId(user1.getId());
//        order.setTime(new Date());
//        order.setPrice(10000);
//        order.setAmount(10);
//        order.setReceiver("wwyy");
//        orderRepository.save(order);
//
//
//        Rebate rebate1=new Rebate();
//        rebate1.setUserId(user1.getId());
//        rebate1.setMerchant(mockMerchant);
//        rebate1.setId(2);
//        rebate1.setScore(rebate1.getScore()==null?1000:rebate1.getScore()+1000);
//        rebate1.setStatus(1);
//        rebate1.setTime(new Date());
//        rebateRepository.save(rebate1);
////      准备测试环境END
//
//
//        Map<String, Object> map=mockMvc.perform(
//                device.getApi("userScoreList")
//                        .param("pageSize","2")
//                        .build()
//        ).andReturn().getModelAndView().getModel();
//        Page<Rebate>  page=( Page<Rebate> )map.get("userScoreList");
//        List<User> list1=userRepository.findAll();
//
//
//
//        Assert.assertEquals("返利积分用户个数测试:", 2, page.getTotalPages() > 1 ? 2 : page.getTotalElements());
//        Assert.assertEquals("返利积分top1测试",99990,(int)page.getContent().get(0).getScore());
//        Assert.assertEquals("返利积分top2测试",1000,(int)page.getContent().get(1).getScore());
//
//
//        map=mockMvc.perform(
//                device.getApi("userExpenditureList")
//                        .param("pageSize","10")
//                        .build()
//        ).andReturn().getModelAndView().getModelMap();
//        Page<Object[]>pageR=( Page<Object[]> )map.get("userExpenditureList");
//        Assert.assertEquals("会员数量测试", 2, pageR.getTotalPages() > 1 ? 10 : pageR.getTotalElements());
//
//    }
//
//    @Test
//    public void testUserExpenditureList() throws Exception {
//
//    }
}