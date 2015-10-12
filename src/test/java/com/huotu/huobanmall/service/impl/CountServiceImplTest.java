package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.service.CountService;
import com.huotu.huobanmall.test.TestWebConfig;
import com.huotu.huobanmall.test.WebTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/9/10.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
@Transactional
public class CountServiceImplTest  extends WebTestBase {
    @Autowired
    CountService countService;
    @Autowired
    MerchantRepository merchantRepository;

    @Test
    public void testTodayOrder() throws Exception {


    }

    @Test
    public void testGetTotalSales() throws Exception {
        Merchant merchant=merchantRepository.findOne(3447);
        double sum=countService.getTotalSales(merchant);
//        Assert.assertEquals(212506.01, sum);

    }

    @Test
    public void testGetTotalOrders() throws Exception {
        Merchant merchant=merchantRepository.findOne(3447);
        long sum=countService.getTotalOrders(merchant);
//        Assert.assertEquals(1511,sum);

    }
}