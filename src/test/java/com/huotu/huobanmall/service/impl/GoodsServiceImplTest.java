package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.service.GoodsService;
import com.huotu.huobanmall.test.TestWebConfig;
import com.huotu.huobanmall.test.WebTestBase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Test
    @Rollback
    public void testSearchProducts() throws Exception {
        //准备测试环境
        Random random=new Random();

        Merchant merchant=new Merchant();
        merchant.setId(100);
        Merchant mockMerchant=merchantRepository.save(merchant);



        Goods product=new Goods();
        int n1=random.nextInt(200);
        product.setId(n1);
        product.setOwner(mockMerchant);
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(1000);
        Goods productNew=productRepository.save(product);                            //新建一个商品

        Goods product1=new Goods();
        int n2=random.nextInt(200);
        product1.setId(n2);
        product1.setOwner(mockMerchant);
        product1.setPrice(200);
        product1.setStatus(1);
        product1.setStock(9999);
        productRepository.save(product1);

        //准备测试环境END

        List<Goods> listAll=productRepository.findAll();
        List<Goods> List=goodsService.searchProducts(mockMerchant,1,6,5).getContent();


    }
}