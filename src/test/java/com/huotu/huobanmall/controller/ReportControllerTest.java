package com.huotu.huobanmall.controller;

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
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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

        for (int i = 1; i <= 22; i++) {
            CountTodaySales countTodaySales = new CountTodaySales();
            countTodaySales.setMerchantId(mockMerchant.getId());
            countTodaySales.setMoney(100 + i * 4);
            countTodaySales.setHour(i);
            countTodaySalesRepository.saveAndFlush(countTodaySales);
        }

        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDaySales countDaySales = new CountDaySales();
            countDaySales.setMerchantId(mockMerchant.getId());
            countDaySales.setMoney(100 + i * 44);
            countDaySales.setDate(date);
            countDaySalesRepository.saveAndFlush(countDaySales);

            date.setTime(date.getTime() + 1000 * 60 * 60 * 24);
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


        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayMember countDayMember = new CountDayMember();
            countDayMember.setMerchantId(mockMerchant.getId());
            countDayMember.setAmount(100 + i * 11);
            countDayMember.setDate(date);
            countDayMemberRepository.saveAndFlush(countDayMember);

            date.setTime(date.getTime() + 1000 * 60 * 60 * 24);
        }


        date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayPartner countDayPartner = new CountDayPartner();
            countDayPartner.setMerchantId(mockMerchant.getId());
            countDayPartner.setAmount(100 + i * 33);
            countDayPartner.setDate(date);
            countDayPartnerRepository.saveAndFlush(countDayPartner);

            date.setTime(date.getTime() + 1000 * 60 * 60 * 24);
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
        mockMvc.perform(device.getApi("otherStatistics")
                .build())
                .andDo(print());


    }
}