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

import junit.framework.Assert;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * 个性化系统
 * Created by lgh on 2015/9/8.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class PersonalControllerTest extends SpringAppTest {


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


    /**
     * 更新商家信息
     * <p>
     * <b>负责人：罗国华</b>
     * <p>Base64 采用Mime标准(http://www.ietf.org/rfc/rfc2045.txt)</p>
     * <p>
     * 更新后的商家信息
     * 0:店铺名称 1:店铺描述 2:店铺logo 3:昵称 4:订单支付成功通知（0关闭,1开启）
     * 5：新增小伙伴通知（0关闭，1开启） 6: 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     * 0:String 1:String 2:Base64(Image) 3:String 4:Number 5:Number 6:Number
     *
     * @throws Exception
     */
    @Test
    public void testUpdateMerchantProfile() throws Exception {

        String shopTitle = "店铺名称abcd";
        mockMvc.perform(device.getApi("updateMerchantProfile")
                .param("profileType", "0")
                .param("profileData", shopTitle)
                .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        Shop shop = shopRepository.findByMerchant(mockMerchant);
        Assert.assertTrue(shopTitle.endsWith(shop.getTitle()));

        //todo 店铺logo

        String shopDescription = "描述abcd";
        mockMvc.perform(device.getApi("updateMerchantProfile")
                .param("profileType", "1")
                .param("profileData", shopDescription)
                .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        shop = shopRepository.findByMerchant(mockMerchant);
        Assert.assertTrue(shopDescription.endsWith(shop.getDiscription()));

        String shopNickName = "昵称abcd";
        mockMvc.perform(device.getApi("updateMerchantProfile")
                .param("profileType", "3")
                .param("profileData", shopNickName)
                .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        Merchant merchant = merchantRepository.findByName(mockMerchantName);
        Assert.assertTrue(shopNickName.endsWith(merchant.getNickName()));


        mockMvc.perform(device.getApi("updateMerchantProfile")
                .param("profileType", "4")
                .param("profileData", "0")
                .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        merchant = merchantRepository.findByName(mockMerchantName);
        Assert.assertTrue("0".endsWith(merchant.isEnableBillNotice() ? "1" : "0"));

        mockMvc.perform(device.getApi("updateMerchantProfile")
                .param("profileType", "5")
                .param("profileData", "0")
                .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        merchant = merchantRepository.findByName(mockMerchantName);
        Assert.assertTrue("0".endsWith(merchant.isEnablePartnerNotice() ? "1" : "0"));

        mockMvc.perform(device.getApi("updateMerchantProfile")
                .param("profileType", "6")
                .param("profileData", "0")
                .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));

        merchant = merchantRepository.findByName(mockMerchantName);
        Assert.assertTrue("0".endsWith(merchant.isNoDisturbed() ? "1" : "0"));

    }

    @Test
    public void testGetMerchantProfile() throws Exception {
        mockMvc.perform(device.getApi("getMerchantProfile").build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.user.name").value(mockMerchantName));
    }
}