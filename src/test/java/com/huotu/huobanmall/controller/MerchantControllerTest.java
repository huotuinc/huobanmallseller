package com.huotu.huobanmall.controller;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.ShopRepository;
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

import java.util.Random;
import java.util.UUID;

import static com.huotu.huobanmall.test.base.Device.huobanmallStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Administrator on 2015/8/31.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class MerchantControllerTest extends SpringAppTest {


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
    public void testInit() throws Exception {
        mockMvc.perform(get("/init")
        ).andExpect(jsonPath("$.resultCode").value(CommonEnum.AppCode.ERROR_USER_LOGIN_FAIL));


//        mockMvc.perform(device.getApi("init")
//                        .build()
//        ).andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(
                device.getApi("login")
                        .param("username", mockMerchantName)
                        .param("password", mockMerchantPassword)
                        .build()

        ).andDo(print());
    }

    @Test
    public void testForgetPassword() throws Exception {
//        merchantService.getAppMerchantModel(true,1);
    }

    @Test
    public void testSendSMS() throws Exception {

    }
}