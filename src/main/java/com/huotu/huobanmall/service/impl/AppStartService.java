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

    @Autowired
    private CountDayMemberRepository countDayMemberRepository;

    @Autowired
    private CountDayOrderRepository countDayOrderRepository;

    @Autowired
    private CountDayPartnerRepository countDayPartnerRepository;

    @Autowired
    private CountDaySalesRepository countDaySalesRepository;

    @Autowired
    private CountTodayMemberRepository countTodayMemberRepository;

    @Autowired
    private CountTodayOrderRepository countTodayOrderRepository;

    @Autowired
    private CountTodayPartnerRepository countTodayPartnerRepository;

    @Autowired
    private CountTodaySalesRepository countTodaySalesRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            if (merchantRepository.count() == 0) {
                generateData("lgh", "18368893860", "lgh");
                generateData("lc", "13675847670", "lc");
                generateData("wlf", "18767152078", "wlf");
                generateData("wl", "15088718256", "wl");
                generateData("jxd", "13757193476", "jxd");
                generateData("jc", "18606509616", "jc");
                generateData("lhb", "13857560740", "lhb");
                generateData("htxx", "18368893861", "htxx");
                generateData("htxx2", "18368893862", "htxx2");
            }
        }
    }


    private void generateData(String merchantName, String operatorName, String userName) {
        Merchant merchant = new Merchant();
        merchant.setName(merchantName);
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
        operator.setName(operatorName);
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
        user.setUsername(userName);
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

        for (int i = 0; i < 29; i++) {
            product = new Goods();
            product.setTitle("商品" + i + "(上架)");
            product.setOwner(merchant);
            product.setPictureUrl("");
            product.setPrice(i * 10);
            product.setStatus(1);
            product.setStock(i * 100);
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


        product = new Goods();
        product.setTitle("商品(库存量无限制)");
        product.setOwner(merchant);
        product.setPictureUrl("");
        product.setPrice(100);
        product.setStatus(1);
        product.setStock(-1);
        product = productRepository.save(product);


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

        for (int i = 1; i <= 12; i++) {
            CountTodayMember countTodayMember = new CountTodayMember();
            countTodayMember.setMerchantId(merchant.getId());
            countTodayMember.setAmount(100 + i);
            countTodayMember.setHour(i);
            countTodayMemberRepository.save(countTodayMember);
        }


        for (int i = 1; i <= 15; i++) {
            CountTodayOrder countTodayOrder = new CountTodayOrder();
            countTodayOrder.setMerchantId(merchant.getId());
            countTodayOrder.setAmount(100 + i * 2);
            countTodayOrder.setHour(i);
            countTodayOrderRepository.save(countTodayOrder);
        }


        for (int i = 1; i <= 18; i++) {
            CountTodayPartner countTodayPartner = new CountTodayPartner();
            countTodayPartner.setMerchantId(merchant.getId());
            countTodayPartner.setAmount(100 + i * 3);
            countTodayPartner.setHour(i);
            countTodayPartnerRepository.save(countTodayPartner);
        }

        for (int i = 1; i <= 22; i++) {
            CountTodaySales countTodaySales = new CountTodaySales();
            countTodaySales.setMerchantId(merchant.getId());
            countTodaySales.setMoney(100 + i * 4);
            countTodaySales.setHour(i);
            countTodaySalesRepository.save(countTodaySales);
        }

        Date date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayMember countDayMember = new CountDayMember();
            countDayMember.setMerchantId(merchant.getId());
            countDayMember.setAmount(100 + i * 11);
            countDayMember.setDate(date);
            countDayMemberRepository.save(countDayMember);

            date.setTime(date.getTime() - 1000 * 60 * 60 * 24);
        }


        date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayOrder countDayOrder = new CountDayOrder();
            countDayOrder.setMerchantId(merchant.getId());
            countDayOrder.setAmount(100 + i * 22);
            countDayOrder.setDate(date);
            countDayOrderRepository.save(countDayOrder);

            date.setTime(date.getTime() - 1000 * 60 * 60 * 24);
        }

        date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDayPartner countDayPartner = new CountDayPartner();
            countDayPartner.setMerchantId(merchant.getId());
            countDayPartner.setAmount(100 + i * 33);
            countDayPartner.setDate(date);
            countDayPartnerRepository.save(countDayPartner);

            date.setTime(date.getTime() - 1000 * 60 * 60 * 24);
        }

        date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 0; i <= 12; i++) {
            CountDaySales countDaySales = new CountDaySales();
            countDaySales.setMerchantId(merchant.getId());
            countDaySales.setMoney(100 + i * 44);
            countDaySales.setDate(date);
            countDaySalesRepository.save(countDaySales);

            date.setTime(date.getTime() - 1000 * 60 * 60 * 24);
        }
    }

}
