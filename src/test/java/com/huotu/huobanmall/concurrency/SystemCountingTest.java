package com.huotu.huobanmall.concurrency;

import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;


import com.huotu.huobanmall.test.base.SpringAppTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

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

    @Test
    public void testCount() throws Exception {

//        systemCounting.count();

        systemCounting.countDay();
    }
}