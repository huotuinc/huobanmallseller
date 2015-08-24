package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppGoodListModel;
import com.huotu.huobanmall.model.app.AppIndexModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 商品系统
 */
public interface GoodsSystem {

    /**
     * app首页 （统计数据）
     *
     * @param index 输出首页统计信息
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult index(Output<AppIndexModel> index) throws Exception;

    /**
     * 商品列表
     *
     * @param list 输出商品列表
     * @param type 类型 1销售中 2已下架
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult goodsList(Output<AppGoodListModel[]> list, Integer type) throws Exception;

    /**
     * 操作商品
     *
     * @param type  操作类型 1 上架商品 2 下架商品 3 删除商品
     * @param goods 商品Id以,隔开 如 1,2,4
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    ApiResult operGoods(Integer type, String goods) throws Exception;

}
