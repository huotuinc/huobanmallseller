/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    CommonConfigService commonConfigService;


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
            appGoodListModel.setPictureUrl(commonConfigService.getResoureServerUrl()+good.getSmallPic());
            appGoodListModel.setStock(good.getStock());
            appGoodListModel.setPrice(good.getPrice());
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

        Date time;
        if (StringUtils.isEmpty(lastDate)) {
            time=null;
        } else {
            time = new Date(lastDate);
        }
//        List<Order> orderList = orderService.searchOrders(merchant.getId(), time, PAGE_SIZE, status, keyword);

        //主订单
        List<MainOrder> mainOrderlist=orderService.searchMainOrders(merchant.getId(),time,PAGE_SIZE,status,keyword);
        //主订单ID
        List<String> mainOrderNo=new ArrayList<>();
        mainOrderlist.forEach(data->{
            mainOrderNo.add(data.getId());
        });

        //子订单
        List<Order> orderList;
        if(mainOrderNo.size()!=0){
            orderList=orderRepository.findByMainOrderNo(mainOrderNo);
            //子订单列表
            AppOrderListModel[] appOrderListModels = new AppOrderListModel[orderList.size()];
            int i = 0;
            for (Order o : orderList) {
                AppOrderListModel appOrderListModel = new AppOrderListModel();
                //规格
                List<OrderItems> orderItemses = orderItemsRepository.findByOrder(o);
                //每个子订单里的商品列表
                List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<>();
                for (int k = 0; k < orderItemses.size(); k++) {
                    AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();

                    OrderItems orderItems = orderItemses.get(k);
                    Product product = productRepository.findOne(orderItems.getProductId());
                    Goods goods = goodsRepository.findOne(orderItems.getGoodsId());

                    if(!StringUtils.isEmpty(orderItems)){
                        appOrderListProductModel.setAmount(orderItems.getAmount());
                    }
                    if(!StringUtils.isEmpty(product)){
                        appOrderListProductModel.setSpec(product.getSpec());
                        appOrderListProductModel.setTitle(product.getName());
                        appOrderListProductModel.setMoney(product.getPrice());
                    }
                    if(!StringUtils.isEmpty(goods)){
                        appOrderListProductModel.setPictureUrl(commonConfigService.getResoureServerUrl()+goods.getSmallPic());
                    }
                    appOrderListProductModels.add(appOrderListProductModel);
                }
                appOrderListModel.setList(appOrderListProductModels);
                appOrderListModel.setOrderNo(o.getId());
                appOrderListModel.setPaid(o.getPrice());
                appOrderListModel.setAmount(o.getAmount());
                appOrderListModel.setMainOrderNo(o.getMainOrderNo());
                switch (status){
                    case 0:
                        appOrderListModel.setStatus(orderService.getPayStatus(o.getPayStatus())+" "+orderService.getDeliverStatus(o.getDeliverStatus()));
                        break;
                    case 1:
                        appOrderListModel.setStatus(orderService.getPayStatus(o.getPayStatus()));
                        break;
                    case 2:
                        appOrderListModel.setStatus(orderService.getDeliverStatus(o.getDeliverStatus()));
                        break;
                    case 3:
                        appOrderListModel.setStatus(orderService.getOrderStatus(o.getStatus()));
                        break;
                    default:
                        appOrderListModel.setStatus("已关闭");
                }
                appOrderListModel.setTime(o.getTime());
                appOrderListModels[i] = appOrderListModel;
                i++;
            }
            list.outputData(appOrderListModels);
        }else {
            list.outputData(null);
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

//    @RequestMapping("/mainOrderList")
//    @Override
//    public ApiResult mainOrderList(Output<AppMainOrderListModel[]> list,Output<AppOrderListModel[]> listz, Integer status,
//                                   @RequestParam(required = false) Long lastDate,
//                                   @RequestParam(required = false) String keyword) throws Exception {
//        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
//
//        Date time;
//        if (StringUtils.isEmpty(lastDate)) {
//            time=null;
//        } else {
//            time = new Date(lastDate);
//        }
//        //主订单
//        List<MainOrder> mainOrderlist=orderService.searchMainOrders(merchant.getId(),time,PAGE_SIZE,status,keyword);
//        //主订单ID
//        List<String> mainOrderNo=new ArrayList<>();
//        mainOrderlist.forEach(data->{
//            mainOrderNo.add(data.getId());
//        });
//
//        //子订单
//        List<Order> orders;
//        if(mainOrderNo.size()!=0){
//            orders=orderRepository.findByMainOrderNo(mainOrderNo);
//        }else {
//            orders=null;
//        }
//        AppMainOrderListModel[] appMainOrderListModels=new AppMainOrderListModel[mainOrderlist.size()];
//        int i=0;
//        for(MainOrder mainOrder:mainOrderlist){
//            AppMainOrderListModel appMainOrderListModel=new AppMainOrderListModel();
//            //设置主订单号
//            appMainOrderListModel.setMainOrderNo(mainOrder.getId());
//
//
//            //存放子订单model
//            List<AppOrderListModel> listModelList = new ArrayList<>();
//            orders.stream().filter(x->x.getMainOrderNo()==mainOrder.getId()).forEach(y->{//y是订单实体
//                AppOrderListModel appOrderListModel = new AppOrderListModel();
//                appOrderListModel.setTime(y.getTime());
//                appOrderListModel.setMainOrderNo(y.getMainOrderNo());
//                appOrderListModel.setAmount(y.getAmount());
//                appOrderListModel.setPaid(y.getPrice());
//
//                //规格
//                List<OrderItems> orderItemses = orderItemsRepository.findByOrder(y);
//                List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<>();
//                for (int k = 0; k < orderItemses.size(); k++) {
//                    AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();
//
//                    OrderItems orderItems = orderItemses.get(k);
//                    Product product = productRepository.findOne(orderItems.getProductId());
//                    Goods goods = goodsRepository.findOne(orderItems.getGoodsId());
//
//                    appOrderListProductModel.setAmount(orderItems.getAmount());
//                    appOrderListProductModel.setSpec(product.getSpec());
//                    appOrderListProductModel.setTitle(product.getName());
//                    appOrderListProductModel.setMoney(product.getPrice());
//                    appOrderListProductModel.setPictureUrl(goods.getPictureUrl());
//
//                    appOrderListProductModels.add(appOrderListProductModel);
//                }
//
//
//                appOrderListModel.setList(appOrderListProductModels);
//                appOrderListModel.setOrderNo(y.getId());
//                switch (status){
//                    case 0:
//                        appOrderListModel.setStatus(orderService.getPayStatus(y.getPayStatus())+" "+orderService.getDeliverStatus(y.getDeliverStatus()));
//                        break;
//                    case 1:
//                        appOrderListModel.setStatus(orderService.getPayStatus(y.getPayStatus()));
//                        break;
//                    case 2:
//                        appOrderListModel.setStatus(orderService.getDeliverStatus(y.getDeliverStatus()));
//                        break;
//                    case 3:
//                        appOrderListModel.setStatus(orderService.getOrderStatus(y.getStatus()));
//                        break;
//                    default:
//                        appOrderListModel.setStatus("已关闭");
//                }
//
//                listModelList.add(appOrderListModel);
//
//
//            });
//            //设置子订单信息
//            appMainOrderListModel.setList(listModelList);
//            appMainOrderListModels[i]=appMainOrderListModel;
//            i++;
//        }
//        list.outputData(appMainOrderListModels);
//
//
//        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
//    }

    @Override
    @RequestMapping("/orderDetail")
    public ApiResult orderDetail(Output<AppOrderDetailModel> data, String orderNo) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        AppOrderDetailModel appOrderDetailModel = new AppOrderDetailModel();
        //获取订单
        Order order = orderRepository.findOne(orderNo);
        User user = userRepository.findOne(order.getUserId());
//        List<Delivery> deliveries = deliveryRepository.findByOrder(order);
        List<Rebate> rebates = rebateRepository.findByMerchantAndOrder(merchant, order);


        //获取该订单的顶单项
        List<OrderItems> orderItemses = orderItemsRepository.findByOrder(order);
        List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<AppOrderListProductModel>();
        for (int i = 0; i < orderItemses.size(); i++) {
            OrderItems orderItems = orderItemses.get(i);
            Product product = productRepository.findOne(orderItems.getProductId());
            Goods goods = goodsRepository.findOne(orderItems.getGoodsId());
            AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();
            appOrderListProductModel.setPictureUrl(commonConfigService.getResoureServerUrl()+goods.getSmallPic());
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
            appUserRebateModel.setUserType(rebateService.getScoreUserName(rebate.getGainer()));
            appUserRebateModel.setRegularization(rebate.getScheduledTime());
            appUserRebateModel.setPresent(rebateService.getScoreStatus(rebate.getStatus()));
            appUserRebateModels.add(appUserRebateModel);

        }

        appOrderDetailModel.setList(appOrderListProductModels);
        appOrderDetailModel.setAmount(order.getAmount());
        appOrderDetailModel.setBuyer(userService.getViewUserName(user));
        appOrderDetailModel.setReceiver(order.getReceiver());
        appOrderDetailModel.setContact(user.getMobile()==null?"暂无联系电话":user.getMobile());
        appOrderDetailModel.setOrderNo(order.getId());
        appOrderDetailModel.setAddress( order.getShipAddr()==null?"暂无地址":order.getShipAddr());
        appOrderDetailModel.setPaid(order.getPrice());
        appOrderDetailModel.setScoreList(appUserRebateModels);
        data.outputData(appOrderDetailModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/logisticsDetail")
    public ApiResult logisticsDetail(Output<AppLogisticsDetailModel> data, String orderNo) throws Exception {
        //获取订单
        Order order = orderRepository.findOne(orderNo);
        //获取该订单的订单项
        List<OrderItems> orderItemses = orderItemsRepository.findByOrder(order);
        List<AppOrderListProductModel> appOrderListProductModels = new ArrayList<>();
        for (int i = 0; i < orderItemses.size(); i++) {
            OrderItems orderItems = orderItemses.get(i);
            Product product = productRepository.findOne(orderItems.getProductId());
            Goods goods = goodsRepository.findOne(orderItems.getGoodsId());
            AppOrderListProductModel appOrderListProductModel = new AppOrderListProductModel();
            appOrderListProductModel.setPictureUrl(commonConfigService.getResoureServerUrl()+goods.getSmallPic());
            appOrderListProductModel.setMoney(product.getPrice());
            appOrderListProductModel.setAmount(orderItems.getAmount());
            appOrderListProductModel.setTitle(product.getName());
            appOrderListProductModel.setSpec(product.getSpec());
            appOrderListProductModels.add(appOrderListProductModel);
        }

        List<Delivery> deliveries = deliveryRepository.findByOrder(order);
        Delivery delivery;
        if (deliveries.size() != 0) {
            delivery = deliveryRepository.findByOrder(order).get(0);
        } else {
            delivery = new Delivery();
        }
        String appId = "73d29a4c9a6d389a0b7288ec27b4c4c4";
        String encryption = "9389e8a5c32eefa3134340640fb4ceaa";
        String sign = DigestUtils.md5DigestAsHex(("appid=" + appId + "&number=" + delivery.getNo() + encryption).getBytes());
        String url = "http://express.51flashmall.com/express/logisty";




        AppLogisticsDetailModel appLogisticsDetailModel = new AppLogisticsDetailModel();
        appLogisticsDetailModel.setSource(StringUtils.isEmpty(delivery.getDeliveryName())? "暂无状态信息" : delivery.getDeliveryName());
        appLogisticsDetailModel.setStatus(StringUtils.isEmpty(delivery.getStatus())? "暂无来源信息" : delivery.getStatus());
        appLogisticsDetailModel.setNo(StringUtils.isEmpty(delivery.getNo())? "暂无编号信息" : delivery.getStatus());

        appLogisticsDetailModel.setList(appOrderListProductModels);
        appLogisticsDetailModel.setPictureURL(null);//todo 到时候图片需要修改

        List<AppLogisticsDataModel> appLogisticsDataModels = new ArrayList<>();
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("appid", appId);
            map.put("sign", sign);
            map.put("number", delivery.getNo());
            //调用post方法获得物流信息字符串

            String   Data = HttpHelper.postRequest(url, map);


            AppLogisticsModel result = JSON.parseObject(Data, AppLogisticsModel.class);
            //获取物流信息集合
            appLogisticsDataModels = result.getData();

        } catch (Exception e) {
            appLogisticsDetailModel.setTrack(null);
        }
        appLogisticsDetailModel.setTrack(appLogisticsDataModels);

        data.outputData(appLogisticsDetailModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/userScoreList")
    public ApiResult userScoreList(Output<AppUserScoreModel[]> list,
                                   @RequestParam(required=false)Integer lastId,
                                   @RequestParam(required=false)String key) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();

        List rebates = rebateService.searchUserScore(apm.getCurrentUser(),lastId,key,PAGE_SIZE);
        AppUserScoreModel[] appUserScoreModels = new AppUserScoreModel[rebates.size()];
        for (int i = 0; i < rebates.size(); i++) {
            AppUserScoreModel appUserScoreModel = new AppUserScoreModel();
            Object[] objects=(Object[])rebates.get(i);
            Rebate rebate=(Rebate)objects[0];
            User user=objects[1]==null?null:(User)objects[1];
            appUserScoreModel.setName(userService.getViewUserName(user));
            appUserScoreModel.setScore(rebate.getFlowScore());
            appUserScoreModel.setPictureUrl(user==null?"":commonConfigService.getResoureServerUrl()+user.getUserFace());
            appUserScoreModel.setTime(rebate.getTime());
            appUserScoreModel.setPid(rebate.getId());
            appUserScoreModels[i]=appUserScoreModel;

        }
        list.outputData(appUserScoreModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @RequestMapping("/salesList")
    @Override
    public ApiResult salesList(Output<AppSalesListModel[]> list,
                               @RequestParam(required = false) Long lastDate,
                               @RequestParam(required = false) String key
                               ) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();

        Date date;
        if(!StringUtils.isEmpty(lastDate)){
            date=new Date(lastDate);
        }else {
            date=null;
        }

        List<Order> orderList = orderService.searchOrdersDetail(merchant.getId(), date, PAGE_SIZE, key).getContent();
        AppSalesListModel[] appSalesListModels = new AppSalesListModel[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            AppSalesListModel appSalesListModel = new AppSalesListModel();
            Order order = orderList.get(i);
            appSalesListModel.setOrderNo(order.getId());
            appSalesListModel.setMoney(order.getPrice());
            appSalesListModel.setTime(order.getTime());
            appSalesListModel.setPictureUrl(null);//todo 订单图片设置
            appSalesListModels[i] = appSalesListModel;
        }
        list.outputData(appSalesListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/userConsumeList")
    public ApiResult userConsumeList(Output<AppConsumeListModel[]> list,
                                     @RequestParam(required = false)Long lastDate,
                                     @RequestParam(required = false)String key) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        Date date;
        if(lastDate!=null){
            date=new Date(lastDate);
        }else {
            date=null;
        }
        List toplist = orderService.searchExpenditureList(merchant,key,date,PAGE_SIZE);
        AppConsumeListModel[] appConsumeListModels = new AppConsumeListModel[toplist.size()];
        for (int i = 0; i < toplist.size(); i++) {
            Object[] objects = (Object[]) toplist.get(i);
            Order order = (Order) objects[0];
            User user = objects[1] != null ? (User) objects[1] : null;
            AppConsumeListModel appConsumeListModel = new AppConsumeListModel();
            appConsumeListModel.setPictureUrl(user == null ? "" :commonConfigService.getResoureServerUrl()+user.getUserFace());
            appConsumeListModel.setName(userService.getViewUserName(user));
            appConsumeListModel.setAmount(1);
            appConsumeListModel.setMoney(order.getPrice());
            appConsumeListModel.setTime(order.getTime());
            appConsumeListModels[i] = appConsumeListModel;
        }
        list.outputData(appConsumeListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
