package com.huotu.huobanmall.concurrency;

import com.huotu.common.DateHelper;
import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;


import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.UserChangeLog;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.repository.UserChangeLogRepository;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import com.huotu.huobanmall.test.boot.TestLoader;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by lgh on 2015/9/12.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class SystemCountingTest extends SpringAppTest {

    @Autowired
    private SystemCounting systemCounting;

    private static final Log log = LogFactory.getLog(SystemCountingTest.class);

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
        curHour.setHours(-1);

        int orderCount = 10;
        for (int i = 0; i < orderCount; i++) {
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setMerchant(mockMerchant);
            order.setTitle("");
            order.setPrice(100);
            order.setAmount(1);
            order.setStatus(1);
            order.setPayStatus(1);
            order.setReceiver("");
            order.setTime(curHour);
            order.setPayTime(curHour);
            orders.add(order);
            orderRepository.saveAndFlush(order);
            curHour.setMinutes(curHour.getMinutes() + 10);
        }

        curHour = DateHelper.getThisHourBegin();
        curHour.setHours(-1);

        int changeCount = 10;
        for (int i = 0; i < changeCount; i++) {
            UserChangeLog log = new UserChangeLog();
            log.setMerchant(mockMerchant);
            log.setTime(curHour);
            log.setChangeType(2);
            userChangeLogs.add(log);
            userChangeLogRepository.saveAndFlush(log);
            curHour.setMinutes(curHour.getMinutes() + 10);
        }

        curHour = DateHelper.getThisHourBegin();
        curHour.setHours(-1);

        changeCount = 10;
        for (int i = 0; i < changeCount; i++) {
            UserChangeLog log = new UserChangeLog();
            log.setMerchant(mockMerchant);
            log.setTime(curHour);
            log.setChangeType(5);
            userChangeLogs.add(log);
            userChangeLogRepository.saveAndFlush(log);
            curHour.setMinutes(curHour.getMinutes() + 10);
        }

        changeCount = 10;
        for (int i = 0; i < changeCount; i++) {
            UserChangeLog log = new UserChangeLog();
            log.setMerchant(mockMerchant);
            log.setTime(curHour);
            log.setChangeType(6);
            userChangeLogs.add(log);
            userChangeLogRepository.saveAndFlush(log);
            curHour.setMinutes(curHour.getMinutes() + 10);
        }

//进行计算
        systemCounting.count();
        systemCounting.countDay();


        //进行Test判断
        Date startHour = DateHelper.getThisHourBegin();
        startHour.setHours(-1);
        Date endHour = DateHelper.getThisHourBegin();

        Long countOrder = orders.stream().filter(x -> x.getTime().getTime() >= startHour.getTime())
                .filter(x -> x.getTime().getTime() < endHour.getTime()).count();

        Float countSales = ((Double) orders.stream().filter(x -> x.getPayTime().getTime() >= startHour.getTime())
                .filter(x -> x.getPayTime().getTime() < endHour.getTime())
                .filter(x -> x.getStatus().equals(1))
                .mapToDouble(x -> x.getPrice()).summaryStatistics().getSum()).floatValue();


        Assert.assertTrue(countOrder == 1);
    }

    @Test
    public void test1() {

        //构建订单数据

        Order order = new Order();
        order.setId("201505061723033841");
        order.setMerchant(mockMerchant);
        order.setTitle("购买abcd2");
        order.setPrice(100);
        order.setAmount(1);
        order.setStatus(1);
        order.setPayStatus(1);
        order.setReceiver("zhangsan");
        order.setTime(new Date());
        orderRepository.save(order);

    }
}