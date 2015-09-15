package com.huotu.huobanmall.test.base;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Shop;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.ShopRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.UUID;


/**
 * Created by lgh on 2015/8/28.
 */

public class SpringAppTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void createMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    protected Merchant generateMerchantWithToken(MerchantRepository merchantRepository, ShopRepository shopRepository, String mockMerchantPassword) {
        Random random = new Random();
        return generateMerchantWithToken(merchantRepository, shopRepository, StringHelper.RandomNo(random, 15), mockMerchantPassword);
    }

    protected Merchant generateMerchantWithToken(MerchantRepository merchantRepository, ShopRepository shopRepository, String mockMerchantName, String mockMerchantPassword) {
        String token = createToken();
        Merchant merchant = new Merchant();
        merchant.setPassword(mockMerchantPassword);
        merchant.setEnableBillNotice(true);
        merchant.setToken(token);
        merchant.setMobile("");
        merchant.setEnabled(true);
        merchant.setEnablePartnerNotice(true);
        merchant.setName(mockMerchantName);
        merchant.setNickName("伙伴商城abc");
        merchant = merchantRepository.saveAndFlush(merchant);

        Shop shop = new Shop();
        shop.setTitle("伙伴商城");
        shop.setDiscription("");
        shop.setLogo("");
        shop.setMerchant(merchant);
        shopRepository.saveAndFlush(shop);

        return merchant;
    }

    protected String createToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
