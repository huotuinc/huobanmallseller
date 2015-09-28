package com.huotu.huobanmall.service.impl;

import com.huotu.common.DateHelper;
import com.huotu.common.model.AppOS;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private GoodsRepository goodsRepository;

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

    @Autowired
    private RebateRepository rebateRepository;

    @Autowired
    private SellLogRepository sellLogRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ConfigAppVersionRepository configAppVersionRepository;

    @Autowired
    private CPARepository cPARepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            if (merchantRepository.count() == 0) {
                Random random = new Random();
                generateData("lgh", "18368893860", "lgh",random);
                generateData("lc", "13675847670", "lc",random);
                generateData("wlf", "18767152078", "wlf",random);
                generateData("wl", "15088718256", "wl",random);
                generateData("jxd", "13757193476", "jxd",random);
                generateData("jc", "18606509616", "jc",random);
                generateData("lhb", "13857560740", "lhb",random);
                generateData("htxx", "18368893861", "htxx",random);
                generateData("htxx2", "18368893862", "htxx2",random);
            }

            if (configAppVersionRepository.count() == 0) {
                ConfigAppVersion configAppVersion = new ConfigAppVersion();
                configAppVersion.setBigError(false);
                configAppVersion.setSourceFileUrl("http://");//todo app地址
                configAppVersion.setVersionNo("1.0.0");
                configAppVersion.setUpdateTime(new Date());
                configAppVersionRepository.save(configAppVersion);
            }


            if (cPARepository.count() == 0) {
                cPARepository.save(Arrays.asList(
                        new CPA("huotu", "火图安卓", "HB2015AD", AppOS.Android),
                        new CPA("huotu", "火图iphone", "HB2015AP", AppOS.iOS)
                ));
            }
        }


    }


    private void generateData(String merchantName, String operatorName, String userName,Random random) {
        Merchant merchant = new Merchant();
        merchant.setName(merchantName);
        merchant.setPassword("e10adc3949ba59abbe56e057f20f883e");
        merchant.setEnableBillNotice(true);
        merchant.setToken("");
        merchant.setMobile("18368893860");
//        merchant.setEnabled(true);
        merchant.setEnablePartnerNotice(true);

        merchant.setNickName("伙伴商城abc");
        merchant = merchantRepository.save(merchant);

//        SellLog sellLog;
//        for(int i=0;i<9;i++){
//            sellLog=new SellLog();
//            sellLog.setMerchantId();
//
//        }


        Operator operator = new Operator();
        operator.setPassword("e10adc3949ba59abbe56e057f20f883e");
        operator.setName(operatorName);
        operator.setMerchant(merchant);
        operator.setAuthority("1,2,3,100");
        operator.setEnableBillNotice(true);
        operator.setEnablePartnerNotice(true);
//        operator.setIsEnabled(true);
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

        Category category = new Category();
        category.setSortId(1);
        category.setTitle("衣服");
        categoryRepository.save(category);

        Goods goods = new Goods();
        goods.setTitle("商品1");
        goods.setOwner(merchant);
        goods.setPictureUrl("");
        goods.setPrice(100);
        goods.setStatus(1);
        goods.setStock(1000);
        goods.setCategory(category);
        goodsRepository.save(goods);


        goods = new Goods();
        goods.setTitle("商品(下架)");
        goods.setOwner(merchant);
        goods.setPictureUrl("");
        goods.setPrice(100);
        goods.setStatus(2);
        goods.setStock(1000);
        goods.setCategory(category);
        goods = goodsRepository.save(goods);

        Order order = new Order();
        order.setId(createOrderNo(random));
        order.setMerchant(merchant);
        order.setUserId(user.getId());
        order.setUserType(0);
        order.setTitle("购买abcd1");
        order.setPrice(goods.getPrice());
        order.setAmount(1);
        order.setStatus(1);
        order.setPayStatus(1);
        order.setReceiver("zhangsan");
        order.setTime(new Date());
        order.setIsTax(1);
        order.setIsProtect(1);
        orderRepository.save(order);


        goods = new Goods();
        goods.setTitle("商品(库存量无限制)");
        goods.setOwner(merchant);
        goods.setPictureUrl("");
        goods.setPrice(100);
        goods.setStatus(1);
        goods.setStock(-1);
        goods = goodsRepository.save(goods);


        order = new Order();
        order.setId(createOrderNo(random));
        order.setMerchant(merchant);
        order.setUserId(user.getId());
        order.setUserType(1);
        order.setTitle("购买abcd2");
        order.setPrice(goods.getPrice());
        order.setAmount(1);
        order.setStatus(1);
        order.setPayStatus(1);
        order.setReceiver("zhangsan");
        order.setTime(new Date());
        order.setIsTax(1);
        order.setIsProtect(1);
        orderRepository.save(order);


        for (int i = 0; i < 2; i++) {
            Product product = new Product();
            product.setMerchant(merchant);
            product.setGoods(goods);
            product.setName("abc" + i);
            product.setPrice(100F);
            product.setSpec("");
            product.setMarketStatus(1);
            product.setIsLocalStock(1);
            product = productRepository.saveAndFlush(product);

            OrderItems orderItems = new OrderItems();
            orderItems.setGoodsId(goods.getId());
            orderItems.setProductId(product.getId());
            orderItems.setAmount(10);
            orderItems.setMerchant(merchant);
            orderItems.setOrder(order);
            orderItems.setPrice(100F);
            orderItemsRepository.save(orderItems);

            SellLog sellLog = new SellLog();
            sellLog.setGoodsId(goods.getId());
            sellLog.setTime(new Date());
            sellLog.setUserId(user.getId());
            sellLog.setAmount(10);
            sellLog.setDesc("desc");
            sellLog.setMerchantId(merchant.getId());
            sellLog.setName("aaa");
            sellLog.setPrice(10F);
            sellLog.setProductId(product.getId());
            sellLog.setProductName(product.getName());
            sellLogRepository.save(sellLog);
        }


        Delivery delivery = new Delivery();
        delivery.setId(createDeliveryNo(random));
        delivery.setOrder(order);
        delivery.setNo(UUID.randomUUID().toString());
        delivery.setStatus("succ");
        delivery.setUser(user);
        delivery.setAddress("address");
        delivery.setDeliveryName("zhangsan");
        delivery.setMoney(100F);
        delivery.setMobile("1384655544444");
        delivery.setReciever("ABC");
        deliveryRepository.save(delivery);


        for (int i = 1; i <= 24; i++) {
            CountTodayMember countTodayMember = new CountTodayMember();
            countTodayMember.setMerchantId(merchant.getId());
            countTodayMember.setAmount(100 + i);
            countTodayMember.setHour(i);
            countTodayMemberRepository.save(countTodayMember);
        }


        for (int i = 1; i <= 24; i++) {
            CountTodayOrder countTodayOrder = new CountTodayOrder();
            countTodayOrder.setMerchantId(merchant.getId());
            countTodayOrder.setAmount(100 + i * 2);
            countTodayOrder.setHour(i);
            countTodayOrderRepository.save(countTodayOrder);
        }


        for (int i = 1; i <= 24; i++) {
            CountTodayPartner countTodayPartner = new CountTodayPartner();
            countTodayPartner.setMerchantId(merchant.getId());
            countTodayPartner.setAmount(100 + i * 3);
            countTodayPartner.setHour(i);
            countTodayPartnerRepository.save(countTodayPartner);
        }

        for (int i = 1; i <= 24; i++) {
            CountTodaySales countTodaySales = new CountTodaySales();
            countTodaySales.setMerchantId(merchant.getId());
            countTodaySales.setMoney(100 + i * 4);
            countTodaySales.setHour(i);
            countTodaySalesRepository.save(countTodaySales);
        }

        Date date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 1; i < 12; i++) {
            CountDayMember countDayMember = new CountDayMember();
            countDayMember.setMerchantId(merchant.getId());
            countDayMember.setAmount(100 + i * 11);
            countDayMember.setDate(date);
            countDayMemberRepository.save(countDayMember);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }


        date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 1; i < 12; i++) {
            CountDayOrder countDayOrder = new CountDayOrder();
            countDayOrder.setMerchantId(merchant.getId());
            countDayOrder.setAmount(100 + i * 22);
            countDayOrder.setDate(date);
            countDayOrderRepository.save(countDayOrder);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }

        date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 1; i < 12; i++) {
            CountDayPartner countDayPartner = new CountDayPartner();
            countDayPartner.setMerchantId(merchant.getId());
            countDayPartner.setAmount(100 + i * 33);
            countDayPartner.setDate(date);
            countDayPartnerRepository.save(countDayPartner);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }

        date = new Date(DateHelper.getThisDayBegin().getTime() - 1000 * 60 * 60 * 24 * 12);
        for (int i = 1; i < 12; i++) {
            CountDaySales countDaySales = new CountDaySales();
            countDaySales.setMerchantId(merchant.getId());
            countDaySales.setMoney(100 + i * 44);
            countDaySales.setDate(date);
            countDaySalesRepository.save(countDaySales);

            date = new Date(date.getTime() + 1000 * 60 * 60 * 24);
        }

        Rebate rebate = new Rebate();
        rebate.setOrder(order);
        rebate.setTime(date);
        rebate.setActualTime(date);
        rebate.setGainer(1);
        rebate.setMerchant(merchant);
        rebate.setScheduledTime(date);
        rebate.setScore(100);
        rebate.setStatus(1);
        rebate.setUserId(user.getId());
        rebateRepository.save(rebate);


    }

    private String createOrderNo(Random random) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime()) + random.nextInt(1000000);
    }

    private String createDeliveryNo(Random random) {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime()) + random.nextInt(1000000);
    }

}
