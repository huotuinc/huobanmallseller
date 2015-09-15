package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.service.GoodsService;
import com.huotu.huobanmall.test.TestWebConfig;
import com.huotu.huobanmall.test.WebTestBase;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * Created by Administrator on 2015/9/9.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestWebConfig.class)
@WebAppConfiguration
@Transactional
public class GoodsServiceImplTest extends WebTestBase {
    @Autowired
    GoodsRepository productRepository;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsRepository goodsRepository;
    @Test
    @Rollback
    public void testSearchProducts() throws Exception {
        //准备测试环境
        Random random=new Random();
        Merchant merchant=new Merchant();
        merchant.setId(100);
        Merchant mockMerchant=merchantRepository.save(merchant);

        Goods goods;
        for(int i=0;i<10;i++){
            goods=new Goods();
            goods.setOwner(mockMerchant);
            goods.setStatus(1);
            goodsRepository.save(goods);
        }
        for(int i=0;i<6;i++){
            goods=new Goods();
            goods.setOwner(mockMerchant);
            goods.setStatus(0);
            goodsRepository.save(goods);
        }

        Long goodAmount=goodsService.countByMerchant(mockMerchant);
        Long expected=10L;
        Assert.assertEquals("商品数量是否正确(上架的)", expected, goodAmount);







    }
}