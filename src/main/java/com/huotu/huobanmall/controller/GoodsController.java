package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.model.app.AppGoodListModel;
import com.huotu.huobanmall.model.app.AppIndexModel;
import com.huotu.huobanmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lgh on 2015/8/27.
 * Update by shiliting on 2015/8/31
 */
@Controller
@RequestMapping("/app")
public class GoodsController implements GoodsSystem {
    @Autowired
    ProductService productService;


    @Override
    @RequestMapping("/index")
    public ApiResult index(Output<AppIndexModel> index) throws Exception {
        AppIndexModel appIndexModel=new AppIndexModel();
        appIndexModel.setGoodsAmount(100);                  //商品数量
        appIndexModel.setDiscributorAmount(50);             //分销商数量
        appIndexModel.setMemberAmount(1000);                //会员数量
        appIndexModel.setSevenBillAmount(789);              //近七日订单量
        appIndexModel.setTodayBillAmount(78954);            //今日订单数
        appIndexModel.setTodayNewUserAmount(25);            //今日新增会员数
        appIndexModel.setTodaySalesAmount(456.23f);         //今日销售总额
        appIndexModel.setTotalSalesAmount(78946.7f);        //总销售额
        appIndexModel.setTodayDiscributorAmount(8796);      //今日分销商数量
        appIndexModel.setSevenSalesAmount(4568.7f);         //近七日销售额

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/goodsList")
    public ApiResult goodsList(Output<AppGoodListModel[]> list, Integer type) throws Exception {
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/operGoods")
    public ApiResult operGoods(Integer type, String goods) throws Exception {
        return null;
    }
}
