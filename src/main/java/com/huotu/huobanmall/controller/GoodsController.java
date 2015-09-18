package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.*;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
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
    public static final int PAGE_SIZE=10;
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
                               Integer type,@RequestParam(required = false)Integer lastProductId) throws Exception {
        //获取当前商家信息
        Merchant merchant=PublicParameterHolder.getParameters().getCurrentUser();
        //获取商家的商品信息集合
        List<Goods> lists=goodsService.searchProducts(merchant,type,lastProductId,PAGE_SIZE).getContent();
        AppGoodListModel[] appGoodListModels=new AppGoodListModel[lists.size()];
        for(int i=0;i<lists.size();i++){
            AppGoodListModel appGoodListModel=new AppGoodListModel();
            Goods good=lists.get(i);
            appGoodListModel.setGoodsId(good.getId());
            appGoodListModel.setTitle(good.getTitle());
            appGoodListModel.setPictureUrl(good.getPictureUrl());
            appGoodListModel.setStock(good.getStock());
            appGoodListModel.setPrice(good.getStock());
            appGoodListModel.setCategory(good.getCategory().getTitle());
            appGoodListModels[i]=appGoodListModel;
        }
        list.outputData(appGoodListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/operGoods")
    public ApiResult operGoods(Integer type, String goods) throws Exception {
        String[] products=goods.split(",");
        for(int i=0;i<products.length;i++){
            Goods product= goodsRepository.findOne(Integer.parseInt(products[i]));
            product.setStatus(type);
            goodsRepository.save(product);
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/newToday")
    public ApiResult newToday(Output<Float> totalSales,
                              Output<Float> todaySales,
                              Output<Integer[]> orderHour,
                              Output<Integer[]> orderAmount,
                              Output<Integer[]> memberHour,
                              Output<Integer[]> memberAmount,
                              Output<Integer[]> partnerHour,
                              Output<Integer[]> partnerAmount,
                              Output<Integer>   todayOrderAmount,
                              Output<Integer>   todayMemberAmount,
                              Output<Integer>   todayPartnerAmount

    ) throws Exception {
        //获取当前商家信息
        Merchant merchant=PublicParameterHolder.getParameters().getCurrentUser();
        //将Map结果分解成两个时间和数量的数组
        //计算今天各个时间段新增的订单数量
        int n=0;
        Map<Integer,Integer> map=countService.todayOrder(merchant);
        Integer[] hoursOrder=new Integer[map.size()];
        Integer[] orders=new Integer[map.size()];

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursOrder[n]=(entry.getKey());
            orders[n]=entry.getValue();
            n++;
        }

        //计算今天各个时间段新增的会员数量
        n=0;
        map=countService.todayMember(merchant);
        Integer[] hoursMember=new Integer[map.size()];
        Integer[] members=new Integer[map.size()];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursMember[n]=(entry.getKey());
            members[n]=entry.getValue();
            n++;
        }
        //计算今天各个时间段新增的小伙伴数量
        n=0;
        map=countService.todayPartner(merchant);
        Integer[] hoursPartner=new Integer[map.size()];
        Integer[] partners=new Integer[map.size()];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursPartner[n]=(entry.getKey());
            partners[n]=entry.getValue();
            n++;
        }

        //返回订单时间段数组
        orderHour.outputData(hoursOrder);
        //返回会员时间段数组
        memberHour.outputData(hoursMember);
        //返回小伙伴时间段数组
        partnerHour.outputData(hoursPartner);
        //返回订单时间段值数组
        orderAmount.outputData(orders);
        //返回会员时间段值数组
        memberAmount.outputData(members);
        //返回小伙伴时间段值数组
        partnerAmount.outputData(partners);
        //返回今日销售额
        todaySales.outputData(orderService.countSale(merchant));
        //返回总销售额
        totalSales.outputData(countService.getTotalSales(merchant));
        //返回今日新增订单数
        todayOrderAmount.outputData(orderService.countOrderQuantity(merchant));
        //返回今日新增会员数
        todayMemberAmount.outputData(userService.countTodayMember(merchant));
        //返回今日新增小伙伴数
        todayPartnerAmount.outputData(userService.countTodayPartner(merchant));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }



    @RequestMapping("/orderList")
    @Override
    public ApiResult orderList(Output<AppOrderListModel[]> list, Integer status, @RequestParam(required = false) Date lastDate) throws Exception {
        Merchant merchant=PublicParameterHolder.getParameters().getCurrentUser();
        if(lastDate==null){
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE,1);
            lastDate=date.getTime();
        }
        List<Order> orderList=orderService.searchOrders(merchant.getId(),lastDate,PAGE_SIZE,status).getContent();
        AppOrderListModel[] appOrderListModels=new AppOrderListModel[orderList.size()];
        int i=0;
        for(Order o:orderList){
            AppOrderListModel appOrderListModel=new AppOrderListModel();
            //规格
            List<OrderItems> orderItemses=orderItemsRepository.findByOrder(o);
            List<AppOrderListProductModel> appOrderListProductModels=new ArrayList<AppOrderListProductModel>();
            for(int k=0;k<orderItemses.size();k++){
                AppOrderListProductModel appOrderListProductModel=new AppOrderListProductModel();

                OrderItems orderItems=orderItemses.get(k);
                Product product=productRepository.findOne(orderItems.getProductId());
                Goods goods=goodsRepository.findOne(orderItems.getGoodsId());

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
            appOrderListModel.setScore(0);//todo 返利积分怎么计算
            appOrderListModels[i]=appOrderListModel;
            i++;
        }
        list.outputData(appOrderListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/orderDetail")
    public ApiResult orderDetail(Output<AppOrderDetailModel> data, String orderNo) throws Exception {
        Merchant merchant=PublicParameterHolder.getParameters().getCurrentUser();
        AppOrderDetailModel appOrderDetailModel=new AppOrderDetailModel();
        //获取订单
        Order order=orderRepository.findOne(orderNo);
        User user=userRepository.findOne(order.getUserId());


        //获取该订单的顶单项
        List<OrderItems> orderItemses=orderItemsRepository.findByOrder(order);
        List<AppOrderListProductModel> appOrderListProductModels=new ArrayList<AppOrderListProductModel>();
        for(int i=0;i<orderItemses.size();i++){
            OrderItems orderItems=orderItemses.get(i);
            Product product=productRepository.findOne(orderItems.getProductId());
            Goods goods=goodsRepository.findOne(orderItems.getGoodsId());
            AppOrderListProductModel appOrderListProductModel=new AppOrderListProductModel();
            appOrderListProductModel.setPictureUrl(goods.getPictureUrl());
            appOrderListProductModel.setMoney(product.getPrice());
            appOrderListProductModel.setAmount(orderItems.getAmount());
            appOrderListProductModel.setTitle(product.getName());
            appOrderListProductModel.setSpec(product.getSpec());
            appOrderListProductModels.add(appOrderListProductModel);
        }
        appOrderDetailModel.setList(appOrderListProductModels);
        appOrderDetailModel.setAmount(order.getAmount());
        appOrderDetailModel.setBuyer(order.getReceiver());
        appOrderDetailModel.setContact(user.getMobile());
        appOrderDetailModel.setOrderNo(order.getId());
        appOrderDetailModel.setAddress("");//todo 收货地址
        appOrderDetailModel.setPaid(order.getPrice());
        data.outputData(appOrderDetailModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/logisticsDetail")
    public ApiResult logisticsDetail(Output<AppLogisticsDetailModel> data, String orderNo) throws Exception {
        String appId ="73d29a4c9a6d389a0b7288ec27b4c4c4";
        String encryption="9389e8a5c32eefa3134340640fb4ceaa";
        String sign= DigestUtils.md5DigestAsHex((appId+ orderNo + encryption).getBytes());
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        String param = "appid="+ appId + "&sign=" +sign+"&number="+orderNo;
//        String returnData=HttpHelper.getRequest("http://express.51flashmall.com/express/logisty", param);

        HttpPost post = new HttpPost("http://express.51flashmall.com/express/logisty");
        post.setEntity(
                EntityBuilder.create()
                .setContentEncoding("UTF-8")
                .setContentType(ContentType.APPLICATION_FORM_URLENCODED)
                .setParameters(
                        new BasicNameValuePair("appid", appId),
                        new BasicNameValuePair("sign", sign),
                        new BasicNameValuePair("number",orderNo)
                )
                .build()
        );
//        HttpResponseProxy resultData=httpClient.execute(post);




        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @RequestMapping("/salesList")
    @Override
    public ApiResult salesList(Output<AppSalesListModel[]> list, @RequestParam(required = false) Long lastDate) throws Exception {
        Merchant merchant=PublicParameterHolder.getParameters().getCurrentUser();
        Date date=new Date(lastDate);
        List<Order> orderList=orderService.searchOrders(merchant.getId(),date,PAGE_SIZE,1).getContent();
        AppSalesListModel[] appSalesListModels=new AppSalesListModel[orderList.size()];
        for(int i=0;i<orderList.size();i++){
            AppSalesListModel appSalesListModel=new AppSalesListModel();
            Order order=orderList.get(i);
            appSalesListModel.setOrderNo(order.getId());
            appSalesListModel.setMoney(order.getPrice());
            appSalesListModel.setTime(order.getTime());
            appSalesListModels[i]=appSalesListModel;
        }
        list.outputData(appSalesListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
