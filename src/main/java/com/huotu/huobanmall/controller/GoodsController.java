package com.huotu.huobanmall.controller;

import com.alibaba.fastjson.JSON;
import com.huotu.common.HttpHelper;
import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

/**
 * Created by lgh on 2015/8/27.
 * Update by shiliting on 2015/8/31
 */
@Controller
@RequestMapping("/app")
public class GoodsController implements GoodsSystem {
    public static final int PAGE_SIZE = 10;
    @Autowired
    GoodsService goodsService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CountTodayOrderRepository countTodayOrderRepository;
    @Autowired
    CountTodayMemberRepository countTodayMemberRepository;
    @Autowired
    CountTodayPartnerRepository countTodayPartnerRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    CountService countService;
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    RebateRepository rebateRepository;
    @Autowired
    RebateService rebateService;


//    @Override
//    @RequestMapping("/index")
//    public ApiResult otherInfo(Output<AppOtherInfoModel> index) throws Exception {
//        //获取当前商家信息
//        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
//        Calendar date = Calendar.getInstance();
//        date.setTime(new Date());
//        date.set(Calendar.HOUR_OF_DAY, 0);
//        date.set(Calendar.SECOND,0);
//        date.set(Calendar.MINUTE,0);
//        //今天
//        Date today=date.getTime();
//        date.set(Calendar.DATE,-5);
//        //近七日
//        Date sevenDays=date.getTime();
//        AppOtherInfoModel appOtherInfoModel=new AppOtherInfoModel();
//        //商品数量
//        appOtherInfoModel.setGoodsAmount(productService.countByMerchant(merchant));
//        //分销商数量
//        appOtherInfoModel.setDiscributorAmount(userService.countUserNumber(merchant,1));
//        //会员数量
//        appOtherInfoModel.setMemberAmount(userService.countUserNumber(merchant,0));
//        //近七日订单量
//        appOtherInfoModel.setSevenBillAmount(orderService.countOrderQuantity(merchant,sevenDays));
//        //今日订单数
//        appOtherInfoModel.setTodayBillAmount(orderService.countOrderQuantity(merchant,today));
//        //今日新增会员数
//        appOtherInfoModel.setTodayNewUserAmount(userService.countUserNumber(merchant,0,today));
//        //今日销售总额
//        appOtherInfoModel.setTodaySalesAmount(orderService.countSale(merchant,today));
//        //总销售额
//        appOtherInfoModel.setTotalSalesAmount(orderService.countSale(merchant));
//        //今日新增分销商数量
//        appOtherInfoModel.setTodayDiscributorAmount(userService.countUserNumber(merchant,1,today));
//        //近七日销售额
//        appOtherInfoModel.setSevenSalesAmount(orderService.countSale(merchant,sevenDays));
//        index.outputData(appOtherInfoModel);
//        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
//    }

    @Override
    @RequestMapping("/goodsList")
    public ApiResult goodsList(Output<AppGoodListModel[]> list,
                               Integer type, @RequestParam(required = false) Integer lastProductId) throws Exception {
        //获取当前商家信息
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        //获取商家的商品信息集合
        List<Goods> lists = goodsService.searchProducts(merchant, type, lastProductId, PAGE_SIZE).getContent();
        AppGoodListModel[] appGoodListModels = new AppGoodListModel[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            AppGoodListModel appGoodListModel = new AppGoodListModel();
            Goods good = lists.get(i);
            appGoodListModel.setGoodsId(good.getId());
            appGoodListModel.setTitle(good.getTitle());
            appGoodListModel.setPictureUrl(good.getPictureUrl());
            appGoodListModel.setStock(good.getStock());
            appGoodListModel.setPrice(good.getStock());
            if (good.getCategory() == null) {
                appGoodListModel.setCategory("未分类");
            } else {
                appGoodListModel.setCategory(good.getCategory().getTitle());
            }
            appGoodListModels[i] = appGoodListModel;
        }
        list.outputData(appGoodListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/operGoods")
    public ApiResult operGoods(Integer type, String goods) throws Exception {
        String[] products = goods.split(",");
        for (int i = 0; i < products.length; i++) {
            Goods product = goodsRepository.findOne(Integer.parseInt(products[i]));
            product.setStatus(type);
            goodsRepository.save(product);
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @RequestMapping("/orderList")
    @Override
    public ApiResult orderList(Output<AppOrderListModel[]> list, Integer status,
                               @RequestParam(required = false) Long lastDate,
                               @RequestParam(required = false) String keyword) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();

        Date time = new Date();
        if (lastDate == null) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 1);
            time = date.getTime();
        } else {
            time = new Date(lastDate);
        }
        List<Order> orderList = orderService.searchOrders(merchant.getId(), time, PAGE_SIZE, status, keyword).getContent();
        AppOrderListModel[] appOrderListModels = new AppOrderListModel[orderList.size()];
        int i = 0;
        for (Order o : orderList) {
            AppOrderListModel appOrderListModel = new AppOrderListModel();
            //规格
            List<OrderItems> orderItemses = orderItemsRepository.findByOrder(o);
            List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<AppOrderListProductModel>();
            for (int k = 0; k < orderItemses.size(); k++) {
                AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();

                OrderItems orderItems = orderItemses.get(k);
                Product product = productRepository.findOne(orderItems.getProductId());
                Goods goods = goodsRepository.findOne(orderItems.getGoodsId());

                appOrderListProductModel.setAmount(orderItems.getAmount());
                appOrderListProductModel.setSpec(product.getSpec());
                appOrderListProductModel.setTitle(product.getName());
                appOrderListProductModel.setMoney(product.getPrice());
                appOrderListProductModel.setPictureUrl(goods.getPictureUrl());

                appOrderListProductModels.add(appOrderListProductModel);
            }
            appOrderListModel.setList(appOrderListProductModels);
            appOrderListModel.setOrderNo(o.getId());
            appOrderListModel.setPaid(o.getPrice());
            appOrderListModel.setAmount(o.getAmount());
            appOrderListModel.setStatus(o.getStatus());
            appOrderListModel.setTime(o.getTime());
            appOrderListModels[i] = appOrderListModel;
            i++;
        }
        list.outputData(appOrderListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/orderDetail")
    public ApiResult orderDetail(Output<AppOrderDetailModel> data, String orderNo) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        AppOrderDetailModel appOrderDetailModel = new AppOrderDetailModel();
        //获取订单
        Order order = orderRepository.findOne(orderNo);
        User user = userRepository.findOne(order.getUserId());
        List<Delivery> deliveries = deliveryRepository.findByOrder(order);
        List<Rebate> rebates = rebateRepository.findByMerchantAndOrder(merchant, order);


        //获取该订单的顶单项
        List<OrderItems> orderItemses = orderItemsRepository.findByOrder(order);
        List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<AppOrderListProductModel>();
        for (int i = 0; i < orderItemses.size(); i++) {
            OrderItems orderItems = orderItemses.get(i);
            Product product = productRepository.findOne(orderItems.getProductId());
            Goods goods = goodsRepository.findOne(orderItems.getGoodsId());
            AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();
            appOrderListProductModel.setPictureUrl(goods.getPictureUrl());
            appOrderListProductModel.setMoney(product.getPrice());
            appOrderListProductModel.setAmount(orderItems.getAmount());
            appOrderListProductModel.setTitle(product.getName());
            appOrderListProductModel.setSpec(product.getSpec());
            appOrderListProductModels.add(appOrderListProductModel);
        }
        List<AppUserRebateModel> appUserRebateModels = new ArrayList<>();
        for (int i = 0; i < rebates.size(); i++) {
            Rebate rebate = rebates.get(i);
            AppUserRebateModel appUserRebateModel = new AppUserRebateModel();
            User rebateUser = userRepository.findOne(rebate.getUserId());
            appUserRebateModel.setUserName(userService.getViewUserName(rebateUser));
            appUserRebateModel.setScore(rebate.getScore());
            appUserRebateModel.setGetTime(rebate.getTime());
            appUserRebateModel.setRegularization(rebate.getActualTime());
            appUserRebateModel.setPresent(rebateService.getScoreStatus(rebate.getStatus()));
            appUserRebateModels.add(appUserRebateModel);

        }

        appOrderDetailModel.setList(appOrderListProductModels);
        appOrderDetailModel.setAmount(order.getAmount());
        appOrderDetailModel.setBuyer(userService.getViewUserName(user));
        appOrderDetailModel.setReceiver(order.getReceiver());
        appOrderDetailModel.setContact(user.getMobile());
        appOrderDetailModel.setOrderNo(order.getId());
        appOrderDetailModel.setAddress(deliveries.size() > 0 ? deliveries.get(0).getAddress() : "暂无地址");
        appOrderDetailModel.setPaid(order.getPrice());
        appOrderDetailModel.setScoreList(appUserRebateModels);
        data.outputData(appOrderDetailModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/logisticsDetail")
    public ApiResult logisticsDetail(Output<AppLogisticsDetailModel> data, String orderNo) throws Exception {
        String appId = "73d29a4c9a6d389a0b7288ec27b4c4c4";
        String encryption = "9389e8a5c32eefa3134340640fb4ceaa";
        String sign = DigestUtils.md5DigestAsHex(("appid=" + appId + "&number=" + orderNo + encryption).getBytes());
        String url = "http://express.51flashmall.com/express/logisty";
        //获取订单
        Order order = orderRepository.findOne(orderNo);
        List<Delivery> deliveries = deliveryRepository.findByOrder(order);

        //获取该订单的顶单项
        List<OrderItems> orderItemses = orderItemsRepository.findByOrder(order);
        List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<AppOrderListProductModel>();
        for (int i = 0; i < orderItemses.size(); i++) {
            OrderItems orderItems = orderItemses.get(i);
            Product product = productRepository.findOne(orderItems.getProductId());
            Goods goods = goodsRepository.findOne(orderItems.getGoodsId());
            AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();
            appOrderListProductModel.setPictureUrl(goods.getPictureUrl());
            appOrderListProductModel.setMoney(product.getPrice());
            appOrderListProductModel.setAmount(orderItems.getAmount());
            appOrderListProductModel.setTitle(product.getName());
            appOrderListProductModel.setSpec(product.getSpec());
            appOrderListProductModels.add(appOrderListProductModel);
        }

        Delivery delivery;
        if (deliveries.size() != 0) {
            delivery = deliveryRepository.findByOrder(order).get(0);
        } else {
            delivery = new Delivery();
        }


        AppLogisticsDetailModel appLogisticsDetailModel = new AppLogisticsDetailModel();
        appLogisticsDetailModel.setSource(delivery.getDeliveryName() == null ? "无" : delivery.getDeliveryName());
        appLogisticsDetailModel.setStatus(delivery.getStatus() == null ? "无" : delivery.getStatus());
        appLogisticsDetailModel.setNo(delivery.getNo() == null ? "无" : delivery.getStatus());

        appLogisticsDetailModel.setList(appOrderListProductModels);
        appLogisticsDetailModel.setPictureURL("http://pic25.nipic.com/20121121/7020518_160535378000_2.jpg");//todo 到时候图片需要修改

        List<AppLogisticsDataModel> appLogisticsDataModels = new ArrayList<>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", appId);
            map.put("sign", sign);
            map.put("number", delivery.getNo());
            //调用post方法获得物流信息字符串
            String Data = null;

            Data = HttpHelper.postRequest(url, map);

            AppLogisticsModel result = JSON.parseObject(Data, AppLogisticsModel.class);
            //获取物流信息集合
            appLogisticsDataModels = result.getData();

        } catch (IOException e) {

        }


        appLogisticsDetailModel.setTrack(appLogisticsDataModels);

        data.outputData(appLogisticsDetailModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @RequestMapping("/salesList")
    @Override
    public ApiResult salesList(Output<AppSalesListModel[]> list, @RequestParam(required = false) Long lastDate) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        Date date = new Date(lastDate);
        List<Order> orderList = orderService.searchOrders(merchant.getId(), date, PAGE_SIZE, 1, "").getContent();
        AppSalesListModel[] appSalesListModels = new AppSalesListModel[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            AppSalesListModel appSalesListModel = new AppSalesListModel();
            Order order = orderList.get(i);
            appSalesListModel.setOrderNo(order.getId());
            appSalesListModel.setMoney(order.getPrice());
            appSalesListModel.setTime(order.getTime());
            appSalesListModels[i] = appSalesListModel;
        }
        list.outputData(appSalesListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
