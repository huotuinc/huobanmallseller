package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.GoodsSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppGoodListModel;
import com.huotu.huobanmall.model.app.AppIndexModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lgh on 2015/8/27.
 */
@Controller
@RequestMapping("/app")
public class GoodsController implements GoodsSystem {


    @Override
    @RequestMapping("/index")
    public ApiResult index(Output<AppIndexModel> index) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/goodsList")
    public ApiResult goodsList(Output<AppGoodListModel[]> list, Integer type) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/operGoods")
    public ApiResult operGoods(Integer type, String goods) throws Exception {
        return null;
    }
}
