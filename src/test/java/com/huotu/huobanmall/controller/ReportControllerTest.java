package com.huotu.huobanmall.controller;

import com.huotu.common.DateHelper;
import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by lgh on 2015/9/9.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class ReportControllerTest extends SpringAppTest {


    private static final Log log = LogFactory.getLog(MerchantControllerTest.class);

    private Device device;

    private Merchant mockMerchant;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ShopRepository shopRepository;

    private String mockMerchantName;
    private String mockMerchantPassword;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CountDayMemberRepository countDayMemberRepository;

    @Autowired
    private CountDayOrderRepository countDayOrderRepository;

    @Autowired
    private CountDayPartnerRepository countDayPartnerRepository;

    @Autowired
    private CountDaySalesRepository countDaySalesRepository;

    @Autowired
    private CountTodayMemberRepository countTodayMemberRepository;

    @Autowired
    private CountTodayOrderRepository countTodayOrderRepository;

    @Autowired
    private CountTodayPartnerRepository countTodayPartnerRepository;

    @Autowired
    private CountTodaySalesRepository countTodaySalesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Before
    public void prepareDevice() {
        device = Device.newDevice(DeviceType.Android);
        Random random = new Random();
        mockMerchantName = StringHelper.RandomNo(random, 15);
        mockMerchantPassword = UUID.randomUUID().toString().replace("-", "");
        mockMerchant = generateMerchantWithToken(merchantRepository, shopRepository, mockMerchantName, mockMerchantPassword);
        device.setToken(mockMerchant.getToken());
    }


    @Test
    public void testNewToday() throws Exception {
        //准备测试环境
        Random random = new Random();
        //设置时间
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        //获取当前小时
        int nowHour = date.get(Calendar.HOUR_OF_DAY);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MINUTE, 0);
        //获取今天起始时间
        Date today = date.getTime();


        //新增商品
        Goods goods = new Goods();
        goods.setOwner(mockMerchant);
        goods.setStatus(1);
        goods.setPrice(100);
        goods = goodsRepository.save(goods);

        User user = new User();
        user.setRegTime(new Date());
        user.setPassword("11");
        user.setUsername("22");
        user.setType(0);
        user.setMerchant(mockMerchant);

        User user1;
        for (int i = 0; i < 5; i++) {
            user1 = new User();
            user1.setRegTime(new Date());
            user1.setPassword("11");
            user1.setUsername("22");
            user1.setType(0);
            user1.setMerchant(mockMerchant);
        }
        for (int i = 0; i < 4; i++) {
            user1 = new User();
            user1.setRegTime(new Date());
            user1.setPassword("11");
            user1.setUsername("22");
            user1.setType(0);
            user1.setMerchant(mockMerchant);
        }

        Order order;
        for (int i = 0; i < 20; i++) {
            order = new Order();
            order.setMerchant(mockMerchant);
            order.setPayStatus(1);
            order.setUserId(user.getId());
            order.setPrice(50);
            order.setAmount(2);
            order.setTime(new Date());
            order.setUserType(0);
        }
        CountTodayOrder countTodayOrder;
        for (int i = 0; i < 24; i++) {
            countTodayOrder = new CountTodayOrder();
            countTodayOrder.setMerchantId(mockMerchant.getId());
            countTodayOrder.setHour(i + 1);
            countTodayOrder.setAmount(i + 1);
            countTodayOrderRepository.save(countTodayOrder);
        }
        CountTodayPartner countTodayPartner;
        for (int i = 0; i < 24; i++) {
            countTodayPartner = new CountTodayPartner();
            countTodayPartner.setMerchantId(mockMerchant.getId());
            countTodayPartner.setHour(i + 1);
            countTodayPartner.setAmount(i + 1);
            countTodayPartnerRepository.save(countTodayPartner);
        }
        CountTodayMember countTodayMember;
        for (int i = 0; i < 24; i++) {
            countTodayMember = new CountTodayMember();
            countTodayMember.setMerchantId(mockMerchant.getId());
            countTodayMember.setHour(i + 1);
            countTodayMember.setAmount(i + 1);
            countTodayMemberRepository.save(countTodayMember);
        }
        CountTodaySales countTodaySales;
        for (int i = 0; i < 10; i++) {
            countTodaySales = new CountTodaySales();
            countTodaySales.setMerchantId(mockMerchant.getId());
            countTodaySales.setHour(i + 1);
            countTodaySales.setMoney(i + 1);
            countTodaySalesRepository.save(countTodaySales);
        }
        CountDaySales countDaySales;
        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        dt.set(Calendar.HOUR_OF_DAY, 0);
        dt.set(Calendar.SECOND, 0);
        dt.set(Calendar.MINUTE, 0);
        for (int i = 0; i < 6; i++) {
            countDaySales = new CountDaySales();
            countDaySales.setMerchantId(mockMerchant.getId());
            dt.set(Calendar.DATE, -5);
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
    public void testOrderReport() throws Exception {

        //创建统计数据
        for (int i = 1; i <= 15; i++) {
            CountTodayOrder countTodayOrder = new CountTodayOrder();
            countTodayOrder.setMerchantId(mockMerchant.getId());
            countTodayOrder.setAmount(100 + i * 2);
            countTodayOrder.setHour(i);
            countTodayOrderRepository.save(countTodayOrder);
        }

        Date date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayOrder countDayOrder = new CountDayOrder();
            countDayOrder.setMerchantId(mockMerchant.getId());
            countDayOrder.setAmount(100 + i * 22);
            countDayOrder.setDate(date);
            countDayOrderRepository.save(countDayOrder);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }


        mockMvc.perform(device.getApi("orderReport")
                .build())
                .andDo(print());
    }

    @Test
    public void testSalesReport() throws Exception {

        for (int i = 1; i <= 22; i++) {
            CountTodaySales countTodaySales = new CountTodaySales();
            countTodaySales.setMerchantId(mockMerchant.getId());
            countTodaySales.setMoney(100 + i * 4);
            countTodaySales.setHour(i);
            countTodaySalesRepository.saveAndFlush(countTodaySales);
        }

        Date date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDaySales countDaySales = new CountDaySales();
            countDaySales.setMerchantId(mockMerchant.getId());
            countDaySales.setMoney(100 + i * 44);
            countDaySales.setDate(date);
            countDaySalesRepository.saveAndFlush(countDaySales);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }

        mockMvc.perform(device.getApi("salesReport")
                .build())
                .andDo(print());
    }

    @Test
    public void testUserReport() throws Exception {

        for (int i = 1; i <= 12; i++) {
            CountTodayMember countTodayMember = new CountTodayMember();
            countTodayMember.setMerchantId(mockMerchant.getId());
            countTodayMember.setAmount(100 + i);
            countTodayMember.setHour(i);
            countTodayMemberRepository.saveAndFlush(countTodayMember);
        }


        for (int i = 1; i <= 18; i++) {
            CountTodayPartner countTodayPartner = new CountTodayPartner();
            countTodayPartner.setMerchantId(mockMerchant.getId());
            countTodayPartner.setAmount(100 + i * 3);
            countTodayPartner.setHour(i);
            countTodayPartnerRepository.saveAndFlush(countTodayPartner);
        }


        Date date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayMember countDayMember = new CountDayMember();
            countDayMember.setMerchantId(mockMerchant.getId());
            countDayMember.setAmount(100 + i * 11);
            countDayMember.setDate(date);
            countDayMemberRepository.saveAndFlush(countDayMember);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }


        date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayPartner countDayPartner = new CountDayPartner();
            countDayPartner.setMerchantId(mockMerchant.getId());
            countDayPartner.setAmount(100 + i * 33);
            countDayPartner.setDate(date);
            countDayPartnerRepository.saveAndFlush(countDayPartner);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }


        mockMvc.perform(device.getApi("userReport")
                .build())
                .andDo(print());

    }

    @Test
    public void myTest() {
        //        mockMvc.perform(device.getApi("salesReport")
//                .build())
//                .andDo(print());

//        Merchant mockMerchant = merchantRepository.findByName("lgh");
//
//        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 24);
//        Map<Date, Integer> mapWeek = new TreeMap<>();
//        List<CountDayOrder> list = countDayOrderRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(mockMerchant.getId(), date);
//        for (CountDayOrder countDayOrder : list) {
//            mapWeek.put(countDayOrder.getDate(), countDayOrder.getAmount());
//        }
//
//
//        if (mapWeek.size() > 0) {
//            Date[] dates = mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]);
//            Integer[] integers = mapWeek.values().toArray(new Integer[mapWeek.values().size()]);
//            long t = mapWeek.values().stream().mapToInt((x) -> x).summaryStatistics().getSum();
//            log.info(t);
//        }
//
//        log.info("size:" + list.size());
    }

    @Test
    public void testOtherStatistics() throws Exception {
        //准备测试环境
//        Random random=new Random();
//        User user;
//        for(int i=0;i<14;i++){
//            user=new User();
//            user.setMerchant(mockMerchant);
//            user.setType(0);
//            user.setUsername("11");
//            user.setPassword("22");
//            user.setRegTime(new Date());
//            userRepository.save(user);
//        }
//        for(int i=0;i<4;i++){
//            user=new User();
//            user.setMerchant(mockMerchant);
//            user.setType(1);
//            user.setUsername("11");
//            user.setPassword("22");
//            user.setRegTime(new Date());
//            userRepository.save(user);
//        }
        Goods goods;
        for (int i = 0; i < 10; i++) {
            goods = new Goods();
            goods.setOwner(mockMerchant);
            goods.setStatus(1);
            goodsRepository.save(goods);
        }
        for (int i = 0; i < 6; i++) {
            goods = new Goods();
            goods.setOwner(mockMerchant);
            goods.setStatus(0);
            goodsRepository.save(goods);
        }
//
//
//        Order order;
//        for(int i=0;i<10;i++){
//            order=new Order();
//            order.setId(String.valueOf(100-i));
//            int k=random.nextInt(3)+1;
//            order.setPayStatus(1);
//            order.setMerchant(mockMerchant);
//            order.setUserId(22);
//            order.setTime(new Date());
//            order.setPrice(25);
//            order.setAmount(10);
//            order.setReceiver("史利挺");
//            orderRepository.save(order);
//        }

        Calendar dt = Calendar.getInstance();
        dt.setTime(new Date());
        dt.set(Calendar.HOUR_OF_DAY, 0);
        dt.set(Calendar.SECOND, 0);
        dt.set(Calendar.MINUTE, 0);
        dt.set(Calendar.DATE, -20);
        CountDayMember countDayMember = new CountDayMember();
        countDayMember.setMerchantId(mockMerchant.getId());
        countDayMember.setDate(dt.getTime());
        countDayMember.setAmount(10);
        countDayMemberRepository.save(countDayMember);

        CountDayOrder countDayOrder = new CountDayOrder();
        countDayOrder.setMerchantId(mockMerchant.getId());
        countDayOrder.setDate(dt.getTime());
        countDayOrder.setAmount(10);
        countDayOrderRepository.save(countDayOrder);


        CountDayPartner countDayPartner = new CountDayPartner();
        countDayPartner.setMerchantId(mockMerchant.getId());
        countDayPartner.setDate(dt.getTime());
        countDayPartner.setAmount(10);
        countDayPartnerRepository.save(countDayPartner);


        //准备测试环境END
        mockMvc.perform(device.getApi("otherStatistics")
                .build())
                .andDo(print())
                .andExpect(jsonPath("$.resultData.otherInfoList.discributorAmount").value(10))
                .andExpect(jsonPath("$.resultData.otherInfoList.memberAmount").value(10))
                .andExpect(jsonPath("$.resultData.otherInfoList.billAmount").value(10))
                .andExpect(jsonPath("$.resultData.otherInfoList.goodsAmount").value(10));
    }

    @Test
    public void testTopScore() throws Exception {
//准备测试环境
        Random random = new Random();
        //设置时间
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        //获取当前小时
        int nowHour = date.get(Calendar.HOUR_OF_DAY);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MINUTE, 0);
        //获取今天起始时间
        Date today = date.getTime();


        //新增商品
        Goods goods = new Goods();
        goods.setOwner(mockMerchant);
        goods.setStatus(1);
        goods.setPrice(100);
        goodsRepository.save(goods);


        User user = new User();
        user.setRegTime(new Date());
        user.setPassword("11");
        user.setUsername("22");
        user.setType(0);
        user.setMerchant(mockMerchant);
        user = userRepository.saveAndFlush(user);

        Order order;
        for (int i = 0; i < 20; i++) {
            order = new Order();
            order.setId("ffffff");
            order.setMerchant(mockMerchant);
            order.setPayStatus(1);
            order.setUserId(user.getId());
            order.setPrice(50);
            order.setAmount(2);
            order.setTime(new Date());
            order.setUserType(0);
            orderRepository.save(order);
        }

        mockMvc.perform(device.getApi("topConsume")
                .build())
                .andDo(print());


    }

    @Test
    public void testTopConsume() throws Exception {

    }

    @Test
    public void testTopSales() throws Exception {

    }

    @Test
    public void TestMonthToWeek() throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        date.setHours(-24 * 30);
//        log.info(date);
//        calendar.setTime(date);
//        log.info(calendar.getTime());
//        log.info(calendar.get(Calendar.WEEK_OF_MONTH));


        List<Date> list = new ArrayList<>();
        Date curDate = DateHelper.getThisDayBegin();

        String curMonth = "2015-7";

        //获取总天数
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateFormat.parse(curMonth));
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        log.info("days:" + days);

        int count = 0;
        for (int i = 1; i <= days; i++) {
            //获取每日数据
            calendar = new GregorianCalendar();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(dateFormat.parse(curMonth + "-" + i));

            int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
            if (k == 1) {// 若当天是周日
                count++;
                System.out.println("-----------------------------------");
                System.out.println("第" + count + "周");
                if (i - 6 <= 1) {
                    System.out.println("本周开始日期:" + curMonth + "-" + 1);
                } else {
                    System.out.println("本周开始日期:" + curMonth + "-" + (i - 6));
                }
                System.out.println("本周结束日期:" + curMonth + "-" + i);
                System.out.println("-----------------------------------");

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                list.add(df.parse(curMonth + "-" + i));
            }
            if (k != 1 && i == days) {// 若是本月最后一天，且不是周日
                count++;
                System.out.println("-----------------------------------");
                System.out.println("第" + count + "周");
                System.out.println("本周开始日期:" + curMonth + "-" + (i - k + 2));
                System.out.println("本周结束日期:" + curMonth + "-" + i);
                System.out.println("-----------------------------------");

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                list.add(df.parse(curMonth + "-" + i));
            }
        }

        for (Date date:list)
        {
            log.info(date);
        }

        if (curDate.getTime() >= calendar.getTime().getTime()) {

        }

    }

}