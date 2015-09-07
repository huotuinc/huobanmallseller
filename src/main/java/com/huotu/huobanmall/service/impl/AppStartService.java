package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lgh on 2015/9/6.
 */

@Service
public class AppStartService implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private GoodsRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            if (merchantRepository.count() == 0) {
                Merchant merchant = new Merchant();
                merchant.setName("huotu");
                merchant.setPassword("e10adc3949ba59abbe56e057f20f883e");
                merchant.setEnableBillNotice(true);
                merchant.setToken("");
                merchant.setMobile("18368893860");
                merchant.setEnabled(true);
                merchant.setEnablePartnerNotice(true);

                merchant.setNickName("伙伴商城abc");
                merchant = merchantRepository.save(merchant);


                Shop shop = new Shop();
                shop.setMerchant(merchant);
                shop.setTitle("伙伴商城");
                shop.setDiscription("");
                shop.setLogo("");
                shopRepository.save(shop);
                User user = new User();
                user.setUsername("liuchen");
                user.setPassword("e10adc3949ba59abbe56e057f20f883e");
                user.setMerchant(merchant);
                user.setRegTime(new Date());
                user.setType(0);
                user = userRepository.save(user);

                Goods product = new Goods();
                product.setTitle("商品1");
                product.setOwner(merchant);
                product.setPictureUrl("");
                product.setPrice(100);
                product.setStatus(1);
                product.setStock(1000);
                productRepository.save(product);

                product = new Goods();
                product.setTitle("商品(下架)");
                product.setOwner(merchant);
                product.setPictureUrl("");
                product.setPrice(100);
                product.setStatus(2);
                product.setStock(1000);
                product = productRepository.save(product);


                product = new Goods();
                product.setTitle("商品(库存量无限制)");
                product.setOwner(merchant);
                product.setPictureUrl("");
                product.setPrice(100);
                product.setStatus(1);
                product.setStock(-1);
                product = productRepository.save(product);


                Order order = new Order();
                order.setId("201505061223033843");
                order.setMerchant(merchant);
                order.setUserId(user.getId());
                order.setUserType(0);
                order.setTitle("购买abcd1");
                order.setPrice(product.getPrice());
                order.setAmount(1);
                order.setOrderStatus(1);
                order.setReceiver("zhangsan");
                order.setTime(new Date());
                orderRepository.save(order);

                order = new Order();
                order.setId("201505061723033841");
                order.setMerchant(merchant);
                order.setUserId(user.getId());
                order.setUserType(1);
                order.setTitle("购买abcd2");
                order.setPrice(product.getPrice());
                order.setAmount(1);
                order.setOrderStatus(0);

                order.setReceiver("zhangsan");
                order.setTime(new Date());
                orderRepository.save(order);

            }

        }
    }

}
