package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.AppGoodListModel;
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
    GoodsService productService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    GoodsRepository productRepository;
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
    CountService countService;


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
        List<Goods> lists=productService.searchProducts(merchant,type,lastProductId,PAGE_SIZE).getContent();
        AppGoodListModel[] appGoodListModels=new AppGoodListModel[lists.size()];
        for(int i=0;i<lists.size();i++){
            AppGoodListModel appGoodListModel=new AppGoodListModel();
            Goods product=lists.get(i);
            appGoodListModel.setGoodsId(product.getId());
            appGoodListModel.setTitle(product.getTitle());
            appGoodListModel.setPictureUrl(product.getPictureUrl());
            appGoodListModel.setStock(product.getStock());
            appGoodListModel.setPrice(product.getStock());
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
            Goods product=productRepository.findOne(Integer.parseInt(products[i]));
            product.setStatus(type);
            productRepository.save(product);
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
        Integer[] hoursOrder=new Integer[nowHour/3];
        Integer[] hoursMember=new Integer[nowHour/3];
        Integer[] hoursPartner=new Integer[nowHour/3];
        Integer[] orders=new Integer[nowHour/3];
        Integer[] members=new Integer[nowHour/3];
        Integer[] partners=new Integer[nowHour/3];
        int n=0;
        Map<Integer,Integer> map=countService.todayOrder(merchant,nowHour);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursOrder[n]=entry.getKey();
            orders[n]=entry.getValue();
            n++;
        }

        n=0;
        map=countService.todayMember(merchant, nowHour);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursMember[n]=entry.getKey();
            members[n]=entry.getValue();
            n++;
        }
        n=0;
        map=countService.todayPartner(merchant, nowHour);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursPartner[n]=entry.getKey();
            partners[n]=entry.getValue();
            n++;
        }

        orderHour.outputData(hoursOrder);
        memberHour.outputData(hoursMember);
        partnerHour.outputData(hoursPartner);
        orderAmount.outputData(orders);
        memberAmount.outputData(members);
        partnerAmount.outputData(partners);
        todaySales.outputData(orderService.countSale(merchant,today));
        totalSales.outputData(orderService.countSale(merchant));
        todayOrderAmount.outputData(orderService.countOrderQuantity(merchant,today));
        todayMemberAmount.outputData(userService.countUserNumber(merchant,0,today));
        todayPartnerAmount.outputData(userService.countUserNumber(merchant,1,today));
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
