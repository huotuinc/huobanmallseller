package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

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


//    @RequestMapping(method = RequestMethod.GET)
//    ApiResult otherInfo(Output<AppGoodListModel[]> list) throws Exception;


    /**
     * 订单管理列表
     *
     * @param list     返回订单列表
     * @param status   订单状态 0 全部 1待付款 2待收货 3已完成
     * @param lastDate 上一个订单的下单时间
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult orderList(Output<AppOrderListModel[]> list, Integer status,
                        @RequestParam(required = false) Long lastDate,
                        @RequestParam(required = false) String keyword) throws Exception;


    /**
     * 订单管理详情
     *
     * @param data    返回订单管理详情
     * @param orderNo 订单号
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult orderDetail(Output<AppOrderDetailModel> data, String orderNo) throws Exception;


    /**
     * 物流详情
     *
     * @param data    返回物流详情数据
     * @param orderNo 订单号
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult logisticsDetail(Output<AppLogisticsDetailModel> data, String orderNo) throws Exception;


    /**
     * 返利积分列表 (含搜索)
     *
     * @param list
     * @param lastId 上一页的最后的Id
     * @param key    搜索关键字
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult userScoreList(Output<AppUserScoreModel[]> list, Integer lastId, String key) throws Exception;


    /**
     * 销售明细列表 (含搜索)
     *
     * @param list     返回销售明细列表
     * @param lastDate 上一个订单的下单时间
     * @param key      搜索关键字
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult salesList(Output<AppSalesListModel[]> list, @RequestParam(required = false) Long lastDate, String key) throws Exception;


    /**
     * 用户消费明细列表 (含搜索)
     * @param list
     * @param lastDate  上一个消费的时间
     * @param key 搜索关键字
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult userConsumeList(Output<AppConsumeListModel[]> list, Long lastDate, String key) throws Exception;

}
