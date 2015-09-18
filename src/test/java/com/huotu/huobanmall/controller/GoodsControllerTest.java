package com.huotu.huobanmall.controller;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Administrator on 2015/9/1.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class GoodsControllerTest extends SpringAppTest {
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
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    CountTodayPartnerRepository countTodayPartnerRepository;
    @Autowired
    CountTodayOrderRepository countTodayOrderRepository;
    @Autowired
    CountTodayMemberRepository countTodayMemberRepository;
    @Autowired
    CountTodaySalesRepository countTodaySalesRepository;
    @Autowired
    CountDaySalesRepository countDaySalesRepository;


    @Before
    public void prepareDevice() {
        device = Device.newDevice(DeviceType.Android);
        Random random = new Random();
        mockMerchantName = StringHelper.RandomNo(random, 15);
        mockMerchantPassword = UUID.randomUUID().toString().replace("-", "");
        mockMerchant = generateMerchantWithToken(merchantRepository,shopRepository, mockMerchantName, mockMerchantPassword);
        device.setToken(mockMerchant.getToken());
    }

    @Test
    public void testIndex() throws Exception {
        //准备测试环境
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
        user.setType(0);
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        user.setMerchant(mockMerchant);
        User userNew=userRepository.save(user);                     //新建一个用户
        user=new User();
        user.setId(random.nextInt(200));
        user.setType(0);
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(sevenDays);
        user.setMerchant(mockMerchant);         //新建一个用户



        Goods product=new Goods();
        product.setId(random.nextInt(200));
        product.setOwner(mockMerchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Goods productNew=productRepository.save(product);                            //新建一个商品

        product=new Goods();
        product.setId(random.nextInt(200));
        product.setOwner(mockMerchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);         //新建一个商品

        Order order;
        for(int i=0;i<20;i++){
            order=new Order();
            order.setId(String.valueOf(100-i));
            int k=random.nextInt(3)+1;
            switch (k){
                case 0:
                    daifukuan++;
                    todayNum++;
                    break;
                case 2:
                    daishouhuo++;
                    sevendays++;
                    break;
                case 1:
                    wancheng++;
                    oldDays++;
            }
            order.setPayStatus(1);
            order.setMerchant(mockMerchant);
            order.setUserId(user.getId());
//            order.setUser(user);
            order.setTime(k==1? new Date():(k==2?testSevenDays:oldTime));
//            order.setProductId(productNew.getId());
//            order.setScore(99);
            order.setPrice(25);
            order.setAmount(10);
            order.setReceiver("史利挺");
            orderRepository.save(order);
        }
        //准备测试环境END
        mockMvc.perform(
                device.getApi("index")
                        .build()).andDo(print());
    }

    @Test
    public void testGoodsList() throws Exception {
        //准备测试环境
        Random random=new Random();
        Category category=new Category();
        category.setTitle("干果");
        Goods product;
        for(int i=0;i<35;i++){
            product=new Goods();
            product.setId(i+1);
            product.setOwner(mockMerchant);
            product.setPrice(200);
            product.setStatus(1);
            product.setCategory(category);
            product.setStock(9999);
            productRepository.save(product);
        }
        //准备测试环境END

        mockMvc.perform(
                device.getApi("goodsList")
                        .param("lastProductId", "10")
                        .build());

    }

    @Test
    public void testOperGoods() throws Exception {

    }

    @Test
    public void testNewToday() throws Exception {
        //准备测试环境
        Random random=new Random();
        //设置时间
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        //获取当前小时
        int nowHour=date.get(Calendar.HOUR_OF_DAY);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MINUTE,0);
        //获取今天起始时间
        Date today=date.getTime();


        //新增商品
        Goods goods=new Goods();
        goods.setOwner(mockMerchant);
        goods.setStatus(1);
        goods.setPrice(100);
        goods=productRepository.save(goods);

        User user=new User();
        user.setRegTime(new Date());
        user.setPassword("11");
        user.setUsername("22");
        user.setType(0);
        user.setMerchant(mockMerchant);

        User user1;
        for(int i=0;i<5;i++){
            user1=new User();
            user1.setRegTime(new Date());
            user1.setPassword("11");
            user1.setUsername("22");
            user1.setType(0);
            user1.setMerchant(mockMerchant);
        }
        for(int i=0;i<4;i++){
            user1=new User();
            user1.setRegTime(new Date());
            user1.setPassword("11");
            user1.setUsername("22");
            user1.setType(0);
            user1.setMerchant(mockMerchant);
        }

        Order order;
        for(int i=0;i<20;i++){
            order=new Order();
            order.setMerchant(mockMerchant);
            order.setPayStatus(1);
            order.setUserId(user.getId());
            order.setPrice(50);
            order.setAmount(2);
            order.setTime(new Date());
            order.setUserType(0);
        }
        CountTodayOrder countTodayOrder;
        for(int i=0;i<24;i++){
            countTodayOrder=new CountTodayOrder();
            countTodayOrder.setMerchantId(mockMerchant.getId());
            countTodayOrder.setHour(i+1);
            countTodayOrder.setAmount(i+1);
            countTodayOrderRepository.save(countTodayOrder);
        }
        CountTodayPartner countTodayPartner;
        for(int i=0;i<24;i++){
            countTodayPartner=new CountTodayPartner();
            countTodayPartner.setMerchantId(mockMerchant.getId());
            countTodayPartner.setHour(i+1);
            countTodayPartner.setAmount(i+1);
            countTodayPartnerRepository.save(countTodayPartner);
        }
        CountTodayMember countTodayMember;
        for (int i=0;i<24;i++){
            countTodayMember=new CountTodayMember();
            countTodayMember.setMerchantId(mockMerchant.getId());
            countTodayMember.setHour(i+1);
            countTodayMember.setAmount(i+1);
            countTodayMemberRepository.save(countTodayMember);
        }
        CountTodaySales countTodaySales;
        for(int i=0;i<10;i++){
            countTodaySales=new CountTodaySales();
            countTodaySales.setMerchantId(mockMerchant.getId());
            countTodaySales.setHour(i+1);
            countTodaySales.setMoney(i+1);
            countTodaySalesRepository.save(countTodaySales);
        }
        CountDaySales countDaySales;
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        dt.set(Calendar.HOUR_OF_DAY, 0);
        dt.set(Calendar.SECOND,0);
        dt.set(Calendar.MINUTE,0);
        for(int i=0;i<6;i++){
            countDaySales=new CountDaySales();
            countDaySales.setMerchantId(mockMerchant.getId());
            dt.set(Calendar.DATE,-5);
            countDaySales.setDate(dt.getTime());
            countDaySales.setMoney(100);
            countDaySalesRepository.save(countDaySales);
        }
        //准备测试环境END
        mockMvc.perform(
                device.getApi("newToday")
                        .build()).andDo(print());

    }

    @Test
    public void testOrderList() throws Exception {
        mockMvc.perform(
                device.getApi("orderList")
                        .build()).andDo(print());

    }

    @Test
    public void testLogisticsDetail() throws Exception {
        mockMvc.perform(
                device.getApi("logisticsDetail")
                        .param("orderNo","804111127951")
                        .build()).andDo(print());

    }
}