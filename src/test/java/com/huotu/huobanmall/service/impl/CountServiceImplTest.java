package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
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

    @Test
    public void testTodayOrder() throws Exception {


    }

    @Test
    public void testGetTotalSales() throws Exception {
        Float i=countService.getTotalSales(new Merchant());


    }
}