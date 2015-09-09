package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppGoodListModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品系统
 */
public interface GoodsSystem {

//    /**
//     * app首页 （统计数据）
//     * <b>负责人：罗国华</b>
//     *
//     * @param index 输出首页统计信息
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    ApiResult otherInfo(Output<AppOtherInfoModel> index) throws Exception;

    /**
     * 商品列表
     * <b>负责人：史利挺</b>
     *
     * @param list 输出商品列表
     * @param type 类型 1销售中 2已下架
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult goodsList(Output<AppGoodListModel[]> list, Integer type, @RequestParam(required = false) Integer lastProductId) throws Exception;

    /**
     * 操作商品
     * <b>负责人：史利挺</b>
     *
     * @param type  操作类型 1 上架商品 2 下架商品 3 删除商品
     * @param goods 商品Id以,隔开 如 1,2,4
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    ApiResult operGoods(Integer type, String goods) throws Exception;

    /**
     * todo 需要修改
     * 返回今日新增数据
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult newToday(Output<Float> totalSales, Output<Float> todaySales
            , Output<Integer[]> orderHour, Output<Integer[]> orderAmount
            , Output<Integer[]> memberHour, Output<Integer[]> memberAmount
            , Output<Integer[]> partnerHour, Output<Integer[]> partnerAmount
    ) throws Exception;

//    @RequestMapping(method = RequestMethod.GET)
//    ApiResult otherInfo(Output<AppGoodListModel[]> list) throws Exception;


}
