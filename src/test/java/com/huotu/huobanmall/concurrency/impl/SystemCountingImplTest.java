package com.huotu.huobanmall.concurrency.impl;

import com.huotu.common.DateHelper;
import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.concurrency.SystemCounting;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.UserChangeLog;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by lgh on 2015/9/17.
 */


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class SystemCountingImplTest extends SpringAppTest {
    @Autowired
    private SystemCounting systemCounting;

    private static final Log log = LogFactory.getLog(SystemCountingImplTest.class);

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
    private OrderRepository orderRepository;

    @Autowired
    private UserChangeLogRepository userChangeLogRepository;


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
    public void testCount() throws Exception {
        //构建订单数据

        List<Order> orders = new ArrayList<>();
        List<UserChangeLog> userChangeLogs = new ArrayList<>();

        Date curHour = DateHelper.getThisHourBegin();
        curHour.setHours(curHour.getHours() - 1);

        int orderCount = 10;
        for (int i = 0; i < orderCount; i++) {
            Date date = new Date(curHour.getTime() + 1000 * 60 * 10 * i);

            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setMerchant(mockMerchant);
            order.setTitle("");
            order.setPrice(100);
            order.setAmount(1);
            order.setStatus(1);
            order.setPayStatus(1);
            order.setReceiver("");
            order.setTime(date);
            order.setPayTime(date);
            orders.add(order);
            orderRepository.saveAndFlush(order);
        }


        int changeCount = 10;
        for (int i = 0; i < changeCount; i++) {
            Date date = new Date(curHour.getTime() + 1000 * 60 * 10 * i);

            UserChangeLog log = new UserChangeLog();
            log.setMerchant(mockMerchant);
            log.setTime(date);
            log.setChangeType(2);
            userChangeLogs.add(log);
            userChangeLogRepository.saveAndFlush(log);

        }

        changeCount = 10;
        for (int i = 0; i < changeCount; i++) {
            Date date = new Date(curHour.getTime() + 1000 * 60 * 10 * i);
            UserChangeLog log = new UserChangeLog();
            log.setMerchant(mockMerchant);
            log.setTime(curHour);
            log.setChangeType(5);
            userChangeLogs.add(log);
            userChangeLogRepository.saveAndFlush(log);

        }

        changeCount = 10;
        for (int i = 0; i < changeCount; i++) {
            Date date = new Date(curHour.getTime() + 1000 * 60 * 10 * i);
            UserChangeLog log = new UserChangeLog();
            log.setMerchant(mockMerchant);
            log.setTime(curHour);
            log.setChangeType(6);
            userChangeLogs.add(log);
            userChangeLogRepository.saveAndFlush(log);
        }

        //进行计算
        systemCounting.count();
        systemCounting.countDay();


        //进行Test判断
        Date startHour = DateHelper.getThisHourBegin();
        startHour.setHours(startHour.getHours() - 1);
        Date endHour = DateHelper.getThisHourBegin();

        Long countOrder = orders.stream().filter(x -> x.getTime().getTime() >= startHour.getTime())
                .filter(x -> x.getTime().getTime() < endHour.getTime()).count();

        Float countSales = ((Double) orders.stream().filter(x -> x.getPayTime().getTime() >= startHour.getTime())
                .filter(x -> x.getPayTime().getTime() < endHour.getTime())
                .filter(x -> x.getStatus().equals(1))
                .mapToDouble(x -> x.getPrice()).summaryStatistics().getSum()).floatValue();


        Long countMember = userChangeLogs.stream().filter(x -> x.getTime().getTime() >= startHour.getTime())
                .filter(x -> x.getTime().getTime() < endHour.getTime())
                .filter(x -> x.getChangeType() == 5).count();

        Long countPartner = userChangeLogs.stream().filter(x -> x.getTime().getTime() >= startHour.getTime())
                .filter(x -> x.getTime().getTime() < endHour.getTime())
                .filter(x -> x.getChangeType() == 2 || x.getChangeType() == 6).count();


        Assert.assertEquals("小时订单量", countOrder.intValue(), countTodayOrderRepository.findByMerchantIdAndHour(mockMerchant.getId(), endHour.getHours()).getAmount().intValue());
        Assert.assertEquals("小时销售额", countSales, countTodaySalesRepository.findByMerchantIdAndHour(mockMerchant.getId(), endHour.getHours()).getMoney());
        Assert.assertEquals("小时会员数", countMember.intValue(), countTodayMemberRepository.findByMerchantIdAndHour(mockMerchant.getId(), endHour.getHours()).getAmount().intValue());
        Assert.assertEquals("小时小伙伴", countPartner.intValue(), countTodayPartnerRepository.findByMerchantIdAndHour(mockMerchant.getId(), endHour.getHours()).getAmount().intValue());


        if (curHour.getHours() == 23) {

            Date today = DateHelper.getThisDayBegin();
            today.setHours(-24);

            Assert.assertEquals("日订单量", countOrder.intValue(), countDayOrderRepository.findByMerchantIdAndDate(mockMerchant.getId(), today).getAmount().intValue());
            Assert.assertEquals("日销售额", countSales, countDaySalesRepository.findByMerchantIdAndDate(mockMerchant.getId(), today).getMoney());
            Assert.assertEquals("日会员数", countMember.intValue(), countDayMemberRepository.findByMerchantIdAndDate(mockMerchant.getId(), today).getAmount().intValue());
            Assert.assertEquals("日小伙伴", countPartner.intValue(), countDayPartnerRepository.findByMerchantIdAndDate(mockMerchant.getId(), today).getAmount().intValue());

            log.info("goto le");
        }
    }



    @Test
    public void testInitHistoryDayAndToday() throws Exception {
        systemCounting.InitHistoryDayAndToday();
    }



    @Test
    public void test1() {

        //构建订单数据

//        Order order = new Order();
//        order.setId("201505061723033841");
//        order.setMerchant(mockMerchant);
//        order.setTitle("购买abcd2");
//        order.setPrice(100);
//        order.setAmount(1);
//        order.setStatus(1);
//        order.setPayStatus(1);
//        order.setReceiver("zhangsan");
//        order.setTime(new Date());
//        orderRepository.save(order);

//        log.info(DateHelper.getThisDayBegin(2015, 8, 31));
//        log.info(new Date(2015, 8, 31));

        Date date = new Date(1442419200000L);
        log.info(date);

    }
}