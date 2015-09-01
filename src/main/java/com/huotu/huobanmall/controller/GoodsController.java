package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Product;
import com.huotu.huobanmall.model.app.AppGoodListModel;
import com.huotu.huobanmall.model.app.AppIndexModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.repository.MerchantRepository;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.repository.ProductRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.MerchantService;
import com.huotu.huobanmall.service.OrderService;
import com.huotu.huobanmall.service.ProductService;
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
    ProductService productService;
    @Autowired
    MerchantService merchantService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    @RequestMapping("/index")
    public ApiResult index(Output<AppIndexModel> index) throws Exception {
        Merchant merchant=merchantRepository.findOne(PublicParameterHolder.getParameters().getCurrentUser().getId());
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MINUTE,0);
        //今天
        Date today=date.getTime();
        date.set(Calendar.DATE,-5);
        //近七日
        Date sevenDays=date.getTime();
        AppIndexModel appIndexModel=new AppIndexModel();
        //商品数量
        appIndexModel.setGoodsAmount(productService.countByMerchant(merchant));
        //分销商数量
        appIndexModel.setDiscributorAmount(50);
        //会员数量
        appIndexModel.setMemberAmount(9999);
        //近七日订单量
        appIndexModel.setSevenBillAmount(orderService.countOrderQuantity(merchant.getId(),sevenDays));
        //今日订单数
        appIndexModel.setTodayBillAmount(orderService.countOrderQuantity(merchant.getId(),today));
        //今日新增会员数
        appIndexModel.setTodayNewUserAmount(25);
        //今日销售总额
        appIndexModel.setTodaySalesAmount(orderService.countSale(merchant.getId(),today));
        //总销售额
        appIndexModel.setTotalSalesAmount(orderService.countSale(merchant.getId()));
        //今日分销商数量
        appIndexModel.setTodayDiscributorAmount(8796);
        //近七日销售额
        appIndexModel.setSevenSalesAmount(orderService.countSale(merchant.getId(),sevenDays));
        index.outputData(appIndexModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/goodsList")
    public ApiResult goodsList(Output<AppGoodListModel[]> list,
                               @RequestParam(required = false) Integer type,Integer lastProductId) throws Exception {
        AppPublicModel appPublicModel = PublicParameterHolder.getParameters();
        Merchant merchant=merchantRepository.findOne(appPublicModel.getCurrentUser().getId());
        List<Product> lists=productService.searchProducts(merchant.getId(),type,lastProductId,PAGE_SIZE).getContent();
        AppGoodListModel[] appGoodListModels=new AppGoodListModel[lists.size()];
        for(int i=0;i<lists.size();i++){
            Product product=lists.get(i);
            appGoodListModels[i].setTitle(product.getTitle());
            appGoodListModels[i].setPictureUrl(product.getPictureUrl());
            appGoodListModels[i].setPrice(product.getPrice());
            appGoodListModels[i].setStock(product.getStock());
        }
        list.outputData(appGoodListModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/operGoods")
    public ApiResult operGoods(Integer type, String goods) throws Exception {
        String[] products=goods.split(",");
        for(int i=0;i<products.length;i++){
            Product product=productRepository.findOne(Integer.parseInt(products[i]));
            product.setStatus(type);
        }
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
