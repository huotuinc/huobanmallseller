package com.huotu.huobanmall.test.app;

import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by lgh on 2015/8/28.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})

@Transactional
public class MerchantSystemTest extends SpringAppTest {

    private static final Log log = LogFactory.getLog(MerchantSystemTest.class);

    private Device device;

    @Before
    public  void  prepareDevice()
    {
        device = Device.newDevice(DeviceType.Android);
    }

    @Test
    public void login() throws Exception {
        mockMvc.perform(
                device.getApi("login")
                        .param("username", "lgh")
                        .param("password", "123456")
                        .build()

        ).andDo(print());

    }


}
