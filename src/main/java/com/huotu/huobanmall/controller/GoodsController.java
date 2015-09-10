package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppGoodListModel;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.GoodsService;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.service.OrderService;
import com.huotu.huobanmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/newToday")
    public ApiResult newToday(Output<Float> totalSales, Output<Float> todaySales, Output<Integer[]> orderHour, Output<Integer[]> orderAmount, Output<Integer[]> memberHour, Output<Integer[]> memberAmount, Output<Integer[]> partnerHour, Output<Integer[]> partnerAmount) throws Exception {
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
//        List<Order> list=orderRepository.findByMerchantAndTimeGreaterThan(merchant, today);






        todaySales.outputData(orderService.countSale(merchant,today));
        totalSales.outputData(orderService.countSale(merchant));



        return null;
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
