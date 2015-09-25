package com.huotu.huobanmall.controller;

import com.huotu.common.StringHelper;
import com.huotu.huobanmall.bootconfig.MvcConfig;
import com.huotu.huobanmall.bootconfig.RootConfig;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.test.base.Device;
import com.huotu.huobanmall.test.base.DeviceType;
import com.huotu.huobanmall.test.base.SpringAppTest;
import com.jayway.jsonpath.JsonPath;
import com.sun.jndi.toolkit.url.Uri;
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

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;

import static com.huotu.huobanmall.test.base.Device.huobanmallStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Administrator on 2015/9/1.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {MvcConfig.class, RootConfig.class})
@Transactional
public class GoodsControllerTest extends SpringAppTest {
    private Log log = LogFactory.getLog(GoodsController.class);

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;
    private String mockMerchantName;
    private String mockMerchantPassword;
    private Device device;
    private Merchant mockMerchant;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    CountTodayPartnerRepository countTodayPartnerRepository;
    @Autowired
    CountTodayOrderRepository countTodayOrderRepository;
    @Autowired
    CountTodayMemberRepository countTodayMemberRepository;
    @Autowired
    CountTodaySalesRepository countTodaySalesRepository;
    @Autowired
    CountDaySalesRepository countDaySalesRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private RebateRepository rebateRepository;

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
    public void testIndex() throws Exception {
        //准备测试环境
        Random random = new Random();
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MINUTE, 0);
        //今天
        Date today = date.getTime();
        date.set(Calendar.DATE, -5);
        //近七日
        Date sevenDays = date.getTime();
        //更久
        Date testSevenDays = new Date(date.getTime().getTime() + 3000000);
        Date oldTime = new Date(date.getTime().getTime() - 3000000);
        int daifukuan = 0;
        int daishouhuo = 0;
        int wancheng = 0;
        Integer todayNum = 0;
        Integer sevendays = 0;
        Integer oldDays = 0;
        User user = new User();
        user.setId(random.nextInt(200));
        user.setType(0);
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(new Date());
        user.setMerchant(mockMerchant);
        User userNew = userRepository.save(user);                     //新建一个用户
        user = new User();
        user.setId(random.nextInt(200));
        user.setType(0);
        user.setUsername("shiliting");
        user.setPassword("123456");
        user.setRegTime(sevenDays);
        user.setMerchant(mockMerchant);         //新建一个用户


        Goods goods = new Goods();
        goods.setId(random.nextInt(200));
        goods.setOwner(mockMerchant);
        goods.setPrice(100);
        goods.setStatus(1);
        goods.setStock(1000);
        Goods productNew = goodsRepository.save(goods);                            //新建一个商品

        goods = new Goods();
        goods.setId(random.nextInt(200));
        goods.setOwner(mockMerchant);
        goods.setPrice(100);
        goods.setStatus(1);
        goods.setStock(1000);         //新建一个商品

        Order order;
        for (int i = 0; i < 20; i++) {
            order = new Order();
            order.setId(String.valueOf(100 - i));
            int k = random.nextInt(3) + 1;
            switch (k) {
                case 0:
                    daifukuan++;
                    todayNum++;
                    break;
                case 2:
                    daishouhuo++;
                    sevendays++;
                    break;
                case 1:
                    wancheng++;
                    oldDays++;
            }
            order.setPayStatus(1);
            order.setMerchant(mockMerchant);
            order.setUserId(user.getId());
//            order.setUser(user);
            order.setTime(k == 1 ? new Date() : (k == 2 ? testSevenDays : oldTime));
//            order.setProductId(productNew.getId());
//            order.setScore(99);
            order.setPrice(25);
            order.setAmount(10);
            order.setReceiver("史利挺");
            order.setIsTax(1);
            order.setIsProtect(1);
            orderRepository.save(order);
        }
        //准备测试环境END
        mockMvc.perform(
                device.getApi("index")
                        .build()).andDo(print());
    }

    @Test
    public void testGoodsList() throws Exception {
        //准备测试环境

        Category category = new Category();
        category.setTitle("干果");
        category = categoryRepository.save(category);

        List<Goods> goodsList = new ArrayList<>();

        Goods goods;
        for (int i = 0; i < 35; i++) {
            goods = new Goods();
            goods.setOwner(mockMerchant);
            goods.setPrice(200);
            goods.setStatus(1);
            goods.setCategory(category);
            goods.setStock(9999);
            goods = goodsRepository.saveAndFlush(goods);
            goodsList.add(goods);
        }

        //排序，否则是乱的
//        Collections.sort(goodsList, new Comparator<Goods>() {
//            @Override
//            public int compare(Goods o1, Goods o2) {
//                return o1.getId().compareTo(o2.getId());
//            }
//        });

        //测试第一页
        String result = mockMvc.perform(
                device.getApi("goodsList")
                        .param("lastProductId", "")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        List<Object> list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", list.size(), 10);
        Assert.assertEquals("第一页第一条数据", JsonPath.read(list.get(0).toString(), "$.goodsId").toString()
                , goodsList.get(goodsList.size() - 1).getId().toString());


        //测试下一页
        result = mockMvc.perform(
                device.getApi("goodsList")
                        .param("lastProductId", String.valueOf(goodsList.get(goodsList.size() - 10).getId()))
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();
        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", list.size(), 10);
        Assert.assertEquals("第二页第一条", JsonPath.read(list.get(0).toString(), "$.goodsId").toString()
                , goodsList.get(goodsList.size() - 11).getId().toString());

    }

    @Test
    public void testOperGoods() throws Exception {

        Category category = new Category();
        category.setTitle("干果");
        category = categoryRepository.save(category);

        String strGoods = "";
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Goods goods = new Goods();
            goods.setOwner(mockMerchant);
            goods.setPrice(200);
            goods.setStatus(1);
            goods.setCategory(category);
            goods.setStock(9999);
            goods = goodsRepository.saveAndFlush(goods);
            strGoods += goods.getId() + ",";
            goodsList.add(goods);
        }

        //  操作类型 1 上架商品 2 下架商品 3 删除商品 商品Id以,隔开 如 1,2,4
        //下架商品
        mockMvc.perform(
                device.getApi("operGoods")
                        .param("type", "2")
                        .param("goods", strGoods)
                        .build())
                .andDo(print())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS));


        Assert.assertEquals("下架的商品", 5
                , goodsRepository.findByOwner(mockMerchant).stream().filter(x -> x.getStatus() == 2).mapToInt(x -> x.getId()).summaryStatistics().getCount());


    }


    @Test
    public void testOrderList() throws Exception {
        Calendar calendar = Calendar.getInstance();
        //构建订单数据
        Category category = new Category();
        category.setTitle("干果");
        category = categoryRepository.save(category);

        Goods goods = new Goods();
        goods.setOwner(mockMerchant);
        goods.setPrice(200);
        goods.setStatus(1);
        goods.setCategory(category);
        goods.setStock(9999);
        goodsRepository.saveAndFlush(goods);

        Product product = new Product();
        product.setMerchant(mockMerchant);
        product.setGoods(goods);
        product.setName("abc");
        product.setPrice(100F);
        product.setSpec("");
        product.setMarketStatus(1);
        product.setIsLocalStock(1);
        productRepository.saveAndFlush(product);

        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        user.setMerchant(mockMerchant);
        user.setRegTime(new Date());
        user.setType(0);
        userRepository.saveAndFlush(user);

        Random random = new Random();
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, i * 5);
            Order order = new Order();
            order.setId(createOrderNo(random));
            order.setMerchant(mockMerchant);
            order.setPayStatus(1);//付款状态  0：未支付|1：已支付|2：已支付至担保方|3：部分付款|4：部分退款|5：全额退款
            if (i == 0) order.setPayStatus(0);

            order.setTime(calendar.getTime());
            order.setPayTime(calendar.getTime());
            order.setUserId(user.getId());
            order.setPrice(100);
            order.setTitle("title");
            order.setAmount(50);
            order.setUserType(1);
            order.setIsTax(1);
            order.setIsProtect(1);
            order = orderRepository.save(order);
            orderList.add(order);

            OrderItems orderItems = new OrderItems();
            orderItems.setGoodsId(goods.getId());
            orderItems.setProductId(product.getId());
            orderItems.setAmount(10);
            orderItems.setMerchant(mockMerchant);
            orderItems.setOrder(order);
            orderItems.setPrice(100F);
            orderItemsRepository.save(orderItems);

            Delivery delivery = new Delivery();
            delivery.setId(createDeliveryNo(random));
            delivery.setOrder(order);
            delivery.setAddress("address");
            delivery.setStatus("");
            delivery.setUser(user);
            delivery.setNo("111212112122121");
            delivery.setMoney(10F);
            deliveryRepository.saveAndFlush(delivery);
        }


        //测试第一页
        String result = mockMvc.perform(
                device.getApi("orderList")
                        .param("status", "0")
                        .param("lastDate", "")
                        .param("keyword", "")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        List<Object> list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", list.size(), 10);
        Assert.assertEquals("第一页第一条数据", JsonPath.read(list.get(0).toString(), "$.orderNo").toString()
                , orderList.get(orderList.size() - 1).getId());


        //测试下一页
        result = mockMvc.perform(
                device.getApi("orderList")
                        .param("status", "0")
                        .param("lastDate", String.valueOf(orderList.get(orderList.size() - 10).getTime().getTime()))
                        .param("keyword", "")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();
        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", list.size(), 10);
        Assert.assertEquals("第二页第一条", JsonPath.read(list.get(0).toString(), "$.orderNo").toString()
                , orderList.get(orderList.size() - 11).getId().toString());


        //测试待付款
        result = mockMvc.perform(
                device.getApi("orderList")
                        .param("status", "1")
                        .param("lastDate", "")
                        .param("keyword", "")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", list.size(), 1);
        Assert.assertEquals("第一页第一条待付款数据", JsonPath.read(list.get(0).toString(), "$.orderNo").toString()
                , orderList.stream().filter(x -> x.getPayStatus().intValue() == 0).findFirst().get().getId());


        //订单详情
        mockMvc.perform(
                device.getApi("orderDetail")
                        .param("orderNo", orderList.stream().findFirst().get().getId())
                        .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.data").exists())
                .andDo(print());


        //订单物流
        mockMvc.perform(
                device.getApi("logisticsDetail")
                        .param("orderNo", orderList.stream().findFirst().get().getId())
//                        .param("orderNo","912971286892")
                        .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.data").exists())
                .andDo(print());


    }


    public void testOrderDetail() throws Exception {


    }


    @Test
    public void testUserScoreList() throws Exception {
        User user = generateUser(userRepository, mockMerchant);
        Order order = generateOrder(orderRepository, mockMerchant, user);

        List<Rebate> rebateList = new ArrayList<>();
//        System.out.println();

        Date date = new Date();
        for (int i = 0; i < 25; i++) {
            Rebate rebate = new Rebate();
            rebate.setOrder(order);
            rebate.setTime(date);
            rebate.setActualTime(date);
            rebate.setGainer(1);
            rebate.setMerchant(mockMerchant);
            rebate.setScheduledTime(date);
            rebate.setScore(100);
            rebate.setStatus(1);
            rebate.setUserId(user.getId());
            rebateRepository.saveAndFlush(rebate);
            rebateList.add(rebate);
        }

        String result = mockMvc.perform(device.getApi("userScoreList")
                .param("lastId", "")
                .param("key", "").build())
                .andDo(print())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andReturn().getResponse().getContentAsString();

        List<Object> list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 10, list.size());
        Assert.assertEquals("第一条数据"
                , String.valueOf(rebateList.get(rebateList.size() - 1).getId().toString())
                , JsonPath.read(list.get(0), "$.pid").toString());


        //测试下一页
        result = mockMvc.perform(
                device.getApi("userScoreList")
                        .param("lastId", String.valueOf(rebateList.get(rebateList.size() - 10).getId()))
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();
        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", list.size(), 10);
        Assert.assertEquals("第二页第一条", JsonPath.read(list.get(0).toString(), "$.pid").toString()
                , rebateList.get(rebateList.size() - 11).getId().toString());


        //搜索测试
        String userName = UUID.randomUUID().toString();
        user.setUsername(userName);
        userRepository.save(user);

        result = mockMvc.perform(
                device.getApi("userScoreList")
                        .param("lastId", "")
                        .param("key", userName)
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 10, list.size());
        Assert.assertEquals("第一条数据"
                , String.valueOf(rebateList.get(rebateList.size() - 1).getId().toString())
                , JsonPath.read(list.get(0), "$.pid").toString());

        //搜索无数据
        result = mockMvc.perform(
                device.getApi("userScoreList")
                        .param("lastId", "")
                        .param("key", userName + "abcttt")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 0, list.size());

    }

    @Test
    public void testSalesList() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Random random = new Random();
        User user = generateUser(userRepository, mockMerchant);
        for (int i = 0; i < 25; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 2 * i);
            Order order = new Order();
            order.setMerchant(mockMerchant);
            order.setId(createOrderNo(random));
            order.setTime(calendar.getTime());
            order.setPayTime(calendar.getTime());
            order.setUserId(user.getId());
            order.setPrice(random.nextInt(1000));
            order.setTitle("title");
            order.setAmount(50);
            order.setUserType(1);
            order.setStatus(1);
            order.setPayStatus(1);
            order.setUserId(user.getId());
            order.setIsTax(1);
            order.setIsProtect(1);
            orderRepository.saveAndFlush(order);
            orderList.add(order);
        }


        String result = mockMvc.perform(device.getApi("salesList")
                .param("lastDate", "")
                .param("key", "").build())
                .andDo(print())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andReturn().getResponse().getContentAsString();

        List<Object> list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 10, list.size());
        Assert.assertEquals("第一条数据"
                , String.valueOf(orderList.get(orderList.size() - 1).getTime().getTime())
                , JsonPath.read(list.get(0), "$.time").toString());


        //测试下一页
        result = mockMvc.perform(
                device.getApi("salesList")
                        .param("lastDate", String.valueOf(orderList.get(orderList.size() - 10).getTime().getTime()))
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();
        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", 10, list.size());
        Assert.assertEquals("第二页第一条", JsonPath.read(list.get(0).toString(), "$.time").toString()
                , String.valueOf(orderList.get(orderList.size() - 11).getTime().getTime()));


        //搜索测试
        String userName = UUID.randomUUID().toString();
        user.setUsername(userName);
        userRepository.save(user);

        result = mockMvc.perform(
                device.getApi("salesList")
                        .param("lastDate", "")
                        .param("key", orderList.get(0).getId())
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 1, list.size());
        Assert.assertEquals("第一条数据"
                , String.valueOf(orderList.get(0).getTime().getTime())
                , JsonPath.read(list.get(0), "$.time").toString());

        //搜索无数据
        result = mockMvc.perform(
                device.getApi("salesList")
                        .param("lastDate", "")
                        .param("key", orderList.get(0).getId() + "abcttt")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 0, list.size());
    }


    @Test
    public void testUserConsumeList() throws Exception {
        List<Order> orderList = new ArrayList<>();
        Random random = new Random();
        User user = generateUser(userRepository, mockMerchant);
        for (int i = 0; i < 25; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 2 * i);
            Order order = new Order();
            order.setMerchant(mockMerchant);
            order.setId(createOrderNo(random));
            order.setTime(calendar.getTime());
            order.setPayTime(calendar.getTime());
            order.setUserId(user.getId());
            order.setPrice(random.nextInt(1000));
            order.setTitle("title");
            order.setAmount(50);
            order.setUserType(1);
            order.setStatus(1);
            order.setPayStatus(1);
            order.setUserId(user.getId());
            order.setIsTax(1);
            order.setIsProtect(1);
            orderRepository.saveAndFlush(order);
            orderList.add(order);
        }

        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });


        String result = mockMvc.perform(device.getApi("userConsumeList")
                .param("lastDate", "")
                .param("key", "").build())
                .andDo(print())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andReturn().getResponse().getContentAsString();

        List<Object> list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 10, list.size());
        Assert.assertEquals("第一条数据"
                , String.valueOf(orderList.get(orderList.size() - 1).getTime().getTime())
                , JsonPath.read(list.get(0), "$.time").toString());


        //测试下一页
        result = mockMvc.perform(
                device.getApi("userConsumeList")
                        .param("lastDate", String.valueOf(orderList.get(orderList.size() - 10).getTime().getTime()))
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();
        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("返回条数", 10, list.size());
        Assert.assertEquals("第二页第一条", JsonPath.read(list.get(0).toString(), "$.time").toString()
                , String.valueOf(orderList.get(orderList.size() - 11).getTime().getTime()));


        //搜索测试
        String userName = UUID.randomUUID().toString();
        user.setUsername(userName);
        userRepository.save(user);

        result = mockMvc.perform(
                device.getApi("userConsumeList")
                        .param("lastDate", "")
                        .param("key", userName)
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 10, list.size());
        Assert.assertEquals("第一条数据"
                , String.valueOf(orderList.get(orderList.size() - 1).getTime().getTime())
                , JsonPath.read(list.get(0), "$.time").toString());

        //搜索无数据
        result = mockMvc.perform(
                device.getApi("userConsumeList")
                        .param("lastDate", "")
                        .param("key", userName + "abcttt")
                        .build())
                .andExpect(jsonPath("$.resultData.list").isArray())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(result, "$.resultData.list");
        Assert.assertEquals("条数", 0, list.size());

    }

    @Test
    public void testOrderList1() throws Exception {
        User user = new User();
        user.setMerchant(mockMerchant);
        user.setUsername("123456");
        user = userRepository.saveAndFlush(user);
        String[] ids = new String[25];
        Date[] times = new Date[25];
        Order order;
        for (int i = 0; i < 25; i++) {
            order = new Order();
            order.setTitle("slt" + i);
            order.setPayStatus(0);
            order.setPrice(100 * i);
            order.setMerchant(mockMerchant);
            times[i] = new Date();
            order.setTime(times[i]);
            order.setAmount(10);
            ids[i] = "shiliting" + i;
            order.setId(ids[i]);
            order.setIsTax(1);
            order.setIsProtect(1);
            order = orderRepository.saveAndFlush(order);
        }
        Long longTime = times[4].getTime();
        mockMvc.perform(
                device.getApi("salesList")
                        .param("key", "shiliting")
                        .param("lastDate", String.valueOf(longTime))
                        .build());


    }

    @Test
    public void testLogisticsDetail() throws Exception {

        //订单物流
        mockMvc.perform(
                device.getApi("logisticsDetail")
//                        .param("orderNo", orderList.stream().findFirst().get().getId())
                        .param("orderNo", "665d1d4e-4f49-4586-acae-e7a49618af17")
                        .build())
                .andDo(print());
    }

    @Test
    public void testOrderList2() throws Exception {
        mockMvc.perform(
                device.getApi("orderList")
                        .param("status", "0")
                        .build())
                .andDo(print());

    }
}