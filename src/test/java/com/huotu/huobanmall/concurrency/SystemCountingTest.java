package com.huotu.huobanmall.concurrency;

import com.huotu.common.DateHelper;
import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;


import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import com.huotu.huobanmall.test.boot.TestLoader;
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
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
            orderRepository.saveAndFlush(order);
        }

        systemCounting.count();
        systemCounting.countDay();
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