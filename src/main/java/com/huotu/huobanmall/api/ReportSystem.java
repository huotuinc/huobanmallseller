package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppBillReportModel;
import com.huotu.huobanmall.model.app.AppMemberReportModel;
import com.huotu.huobanmall.model.app.AppSalesReportModel;
import com.huotu.huobanmall.model.app.AppTopBuyAmountModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 统计报表系统
 * Created by Administrator on 2015/8/24.
 */
public interface ReportSystem {

    /**
     * 订单统计报表
     *
     * @param data
     * @param type 1本周 2本月
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult billReport(Output<AppBillReportModel> data, Integer type) throws Exception;


    /**
     * 购买量排行
     *
     * @param top
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult topBuyAmount(Output<AppTopBuyAmountModel> top) throws Exception;

    /**
     * 销售额统计报表
     *
     * @param data
     * @param type 1本周 2本月
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult salesReport(Output<AppSalesReportModel> data, Integer type) throws Exception;


    /**
     * 会员量统计
     *
     * @param member
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult memberReport(Output<AppMemberReportModel> member) throws Exception;
}
