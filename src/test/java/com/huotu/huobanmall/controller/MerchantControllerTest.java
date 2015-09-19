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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.DigestUtils;

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
//        mockMvc.perform(get("/init"))
//                .andDo(print())
//                .andExpect(jsonPath("$.resultCode").value(CommonEnum.AppCode.ERROR_USER_LOGIN_FAIL));


        mockMvc.perform(device.getApi("init")
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(
                device.getApi("login")
                        .param("username", mockMerchantName)
                        .param("password", mockMerchantPassword)
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        //token已经失效
        mockMvc.perform(
                device.getApi("login")
                        .param("username", mockMerchantName)
                        .param("password", mockMerchantPassword)
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.ERROR_USER_TOKEN_FAIL));

        //创建新设备请求 密码错误
        device = Device.newDevice(DeviceType.Android);
        Random random = new Random();
        mockMerchantName = StringHelper.RandomNo(random, 15);
        mockMerchantPassword = UUID.randomUUID().toString().replace("-", "");
        mockMerchant = generateMerchantWithToken(merchantRepository, shopRepository, mockMerchantName, mockMerchantPassword);
        device.setToken(mockMerchant.getToken());
        mockMvc.perform(
                device.getApi("login")
                        .param("username", mockMerchantName)
                        .param("password", UUID.randomUUID().toString())
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.ERROR_WRONG_USERNAME_PASSWORD));
    }

    @Test
    public void testForgetPassword() throws Exception {
        mockMvc.perform(
                device.getApi("forgetPassword")
                        .param("phone", mockMerchantName)
                        .param("password", DigestUtils.md5DigestAsHex("123456".getBytes()))
                        .param("authcode", "123456")
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.ERROR_NO_EXIST_USERNAME));

        //todo 需要模拟操作员
    }

    @Test
    public void testSendSMS() throws Exception {

    }

    @Test
    public void testModifyPassword() throws Exception {

        String newPassword = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
        mockMvc.perform(
                device.getApi("modifyPassword")
                        .param("oldPassword", mockMerchantPassword)
                        .param("newPassword", newPassword)
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        Merchant merchant = merchantRepository.findByName(mockMerchantName);
        Assert.assertEquals("密码修改成功", newPassword, merchant.getPassword());


        mockMvc.perform(
                device.getApi("modifyPassword")
                        .param("oldPassword", mockMerchantPassword)
                        .param("newPassword", newPassword)
                        .build()
        ).andExpect(huobanmallStatus(CommonEnum.AppCode.ERROR_WRONG_PASSWORD));


    }
}