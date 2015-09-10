package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    @Transactional
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


                Operator operator = new Operator();
                operator.setPassword("e10adc3949ba59abbe56e057f20f883e");
                operator.setName("18368893860");
                operator.setMerchant(merchant);
                operator.setAuthority("11,22,33");
                operator.setEnableBillNotice(true);
                operator.setEnablePartnerNotice(true);
                operator.setIsEnabled(true);
                operator.setNoDisturbed(true);
                operatorRepository.save(operator);

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

                for(int i=0;i<29;i++){
                    product = new Goods();
                    product.setTitle("商品"+i+"(上架)");
                    product.setOwner(merchant);
                    product.setPictureUrl("");
                    product.setPrice(i*10);
                    product.setStatus(1);
                    product.setStock(i*100);
                    productRepository.save(product);
                }

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


                merchant = new Merchant();
                merchant.setName("huoban");
                merchant.setPassword("e10adc3949ba59abbe56e057f20f883e");
                merchant.setEnableBillNotice(true);
                merchant.setToken("");
                merchant.setMobile("18368893861");
                merchant.setEnabled(true);
                merchant.setEnablePartnerNotice(true);

                merchant.setNickName("伙伴商城b");
                merchant = merchantRepository.save(merchant);


                shop = new Shop();
                shop.setMerchant(merchant);
                shop.setTitle("伙伴商城b");
                shop.setDiscription("");
                shop.setLogo("");
                shopRepository.save(shop);
                user = new User();
                user.setUsername("luohaibo");
                user.setPassword("e10adc3949ba59abbe56e057f20f883e");
                user.setMerchant(merchant);
                user.setRegTime(new Date());
                user.setType(0);
                user = userRepository.save(user);

                product = new Goods();
                product.setTitle("商品b");
                product.setOwner(merchant);
                product.setPictureUrl("");
                product.setPrice(100);
                product.setStatus(1);
                product.setStock(1000);
                productRepository.save(product);

                product = new Goods();
                product.setTitle("商品b(下架)");
                product.setOwner(merchant);
                product.setPictureUrl("");
                product.setPrice(100);
                product.setStatus(2);
                product.setStock(1000);
                product = productRepository.save(product);


                product = new Goods();
                product.setTitle("商品b(库存量无限制)");
                product.setOwner(merchant);
                product.setPictureUrl("");
                product.setPrice(100);
                product.setStatus(1);
                product.setStock(-1);
                product = productRepository.save(product);


                order = new Order();
                order.setId("201506061223033843");
                order.setMerchant(merchant);
                order.setUserId(user.getId());
                order.setUserType(0);
                order.setTitle("购买abcd1");
                order.setPrice(product.getPrice());
                order.setAmount(1);
                order.setOrderStatus(1);
                order.setReceiver("lisi");
                order.setTime(new Date());
                orderRepository.save(order);

                order = new Order();
                order.setId("201506061723033841");
                order.setMerchant(merchant);
                order.setUserId(user.getId());
                order.setUserType(1);
                order.setTitle("购买abcd2");
                order.setPrice(product.getPrice());
                order.setAmount(1);
                order.setOrderStatus(0);

                order.setReceiver("lis");
                order.setTime(new Date());
                orderRepository.save(order);

            }



        }
    }

}
