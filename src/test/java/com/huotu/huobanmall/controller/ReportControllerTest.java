package com.huotu.huobanmall.controller;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.CountDayOrderRepository;
import com.huotu.huobanmall.repository.CountTodayOrderRepository;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import junit.framework.TestCase;
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
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
    private CountTodayOrderRepository countTodayOrderRepository;

    @Autowired
    private CountDayOrderRepository countDayOrderRepository;

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
    public void testOrderReport() throws Exception {

        //创建统计数据
        for (int i = 1; i <= 15; i++) {
            CountTodayOrder countTodayOrder = new CountTodayOrder();
            countTodayOrder.setMerchantId(mockMerchant.getId());
            countTodayOrder.setAmount(100 + i * 2);
            countTodayOrder.setHour(i);
            countTodayOrderRepository.saveAndFlush(countTodayOrder);
        }

        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayOrder countDayOrder = new CountDayOrder();
            countDayOrder.setMerchantId(mockMerchant.getId());
            countDayOrder.setAmount(100 + i * 22);
            countDayOrder.setDate(date);
            countDayOrderRepository.saveAndFlush(countDayOrder);

            date.setTime(date.getTime() + 1000 * 60 * 60 * 24);
        }


        mockMvc.perform(device.getApi("orderReport")
                .build())
                .andDo(print());
    }

    @Test
    public void testSalesReport() throws Exception {
//        mockMvc.perform(device.getApi("salesReport")
//                .build())
//                .andDo(print());

        Merchant merchant = merchantRepository.findByName("lgh");

        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 24);
        Map<Date, Integer> mapWeek = new TreeMap<>();
        List<CountDayOrder> list = countDayOrderRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDayOrder countDayOrder : list) {
            mapWeek.put(countDayOrder.getDate(), countDayOrder.getAmount());
        }


        if (mapWeek.size() > 0) {
            Date[] dates = mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]);
            Integer[] integers = mapWeek.values().toArray(new Integer[mapWeek.values().size()]);
            long t = mapWeek.values().stream().mapToInt((x) -> x).summaryStatistics().getSum();
            log.info(t);
        }

        log.info("size:" + list.size());
    }

//    private <T> T[] TypeFromObject(Object[] objects) {
//        List<T> result = new ArrayList<>();
//        for (Object o : objects) {
//            result.add((T)o);
//        }
//        return result.toArray(T[result.size()]);
//    }

    @Test
    public void testUserReport() throws Exception {

        mockMvc.perform(device.getApi("userReport")
                .build())
                .andDo(print());

    }
}