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
                , goodsRepository.findAll().stream().filter(x -> x.getOwner().equals(mockMerchant)).filter(x -> x.getStatus() == 2).mapToInt(x -> x.getId()).summaryStatistics().getCount());


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
        productRepository.saveAndFlush(product);

        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        user.setMerchant(mockMerchant);
        user.setRegTime(new Date());
        user.setType(0);
        userRepository.saveAndFlush(user);


        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            calendar = Calendar.getInstance();
            calendar.set(Calendar.MINUTE, i * 5);
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
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
            order = orderRepository.save(order);
            orderList.add(order);

            OrderItems orderItems = new OrderItems();
            orderItems.setGoodsId(goods.getId());
            orderItems.setProductId(product.getId());
            orderItems.setAmount(10);
            orderItems.setMerchant(mockMerchant);
            orderItems.setOrder(order);
            orderItemsRepository.save(orderItems);

            Delivery delivery = new Delivery();
            delivery.setOrder(order);
            delivery.setAddress("address");
            delivery.setStatus("");
            delivery.setUser(user);
            delivery.setNo("111212112122121");
            deliveryRepository.save(delivery);
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
                        .build())
                .andExpect(huobanmallStatus(CommonEnum.AppCode.SUCCESS))
                .andExpect(jsonPath("$.resultData.data").exists())
                .andDo(print());


    }
}