package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.OrderItems;
import com.huotu.huobanmall.model.app.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
        //获取商家的商品信息集合
        List<Goods> lists=goodsService.searchProducts(merchant,type,lastProductId,PAGE_SIZE).getContent();
        AppGoodListModel[] appGoodListModels=new AppGoodListModel[lists.size()];
        for(int i=0;i<lists.size();i++){
            AppGoodListModel appGoodListModel=new AppGoodListModel();
            Goods product=lists.get(i);
            appGoodListModel.setGoodsId(product.getId());
            appGoodListModel.setTitle(product.getTitle());
            appGoodListModel.setPictureUrl(product.getPictureUrl());
            appGoodListModel.setStock(product.getStock());
            appGoodListModel.setPrice(product.getStock());
            appGoodListModel.setCategory(product.getCategory().getTitle());
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
        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        int nowHour=date.get(Calendar.HOUR_OF_DAY);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MINUTE,0);
        //今天
        Date today=date.getTime();

        //定义数组大小
        Integer[] hoursOrder=new Integer[(nowHour+2)/3];
        Integer[] hoursMember=new Integer[(nowHour+2)/3];
        Integer[] hoursPartner=new Integer[(nowHour+2)/3];
        Integer[] orders=new Integer[(nowHour+2)/3];
        Integer[] members=new Integer[(nowHour+2)/3];
        Integer[] partners=new Integer[(nowHour+2)/3];


        //将Map结果分解成两个时间和数量的数组
        //计算今天各个时间段新增的订单数量
        int n=0;
        Map<Integer,Integer> map=countService.todayOrder(merchant);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursOrder[n]=(entry.getKey()+1)*3;
            orders[n]=entry.getValue();
            n++;
        }

        //计算今天各个时间段新增的会员数量
        n=0;
        map=countService.todayMember(merchant);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursMember[n]=(entry.getKey()+1)*3;
            members[n]=entry.getValue();
            n++;
        }
        //计算今天各个时间段新增的小伙伴数量
        n=0;
        map=countService.todayPartner(merchant);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursPartner[n]=(entry.getKey()+1)*3;
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
        List<Order> orderList=orderService.searchOrders(merchant.getId(),lastDate,PAGE_SIZE,status).getContent();
        AppOrderListModel[] appOrderListModels=new AppOrderListModel[orderList.size()];
        int i=0;
        for(Order o:orderList){
            AppOrderListModel appOrderListModel=new AppOrderListModel();
            List<OrderItems> orderItemses=orderItemsRepository.findByOrder(o);
            AppOrderListProductModel[] appOrderListProductModels=new AppOrderListProductModel[orderItemses.size()];
//            for(OrderItems oi:orderItemses){
//                AppOrderListProductModel appOrderListProductModel=new AppOrderListProductModel();
//                appOrderListProductModel.setSpec(productService.);
//            }
//            appOrderListModel.setTitle(o.getTitle());
            appOrderListModel.setTime(o.getTime());
            appOrderListModel.setAmount(o.getAmount());
//            appOrderListModel.setMoney(o.getPrice());
            appOrderListModel.setOrderNo(o.getId());
//            appOrderListModel.setPictureUrl(o.getPictureUrl());
//            appOrderListModel.setReceiver(o.getReceiver());
            appOrderListModel.setStatus(o.getStatus());
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
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/logisticsDetail")
    public ApiResult logisticsDetail(Output<AppLogisticsDetailModel> data, String orderNo) throws Exception {
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @RequestMapping("/salesList")
    @Override
    public ApiResult salesList(Output<AppSalesListModel[]> list, @RequestParam(required = false) Date lastDate) throws Exception {
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

//    @Override
//    public ApiResult newToday(Output<AppGoodListModel[]> list) throws Exception {
//        return null;
//    }
//
//    @Override
//    public ApiResult otherInfo(Output<AppGoodListModel[]> list) throws Exception {
//        return null;
//    }
}
