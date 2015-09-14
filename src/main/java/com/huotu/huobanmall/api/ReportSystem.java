package com.huotu.huobanmall.api;

import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppOtherInfoModel;
import com.huotu.huobanmall.model.app.AppTopConsumeModel;
import com.huotu.huobanmall.model.app.AppTopSalesModel;
import com.huotu.huobanmall.model.app.AppTopScoreModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 统计报表系统
 */
public interface ReportSystem {

//    /**
//     * 订单统计报表
//     * <b>：罗国华</b>
//     *
//     * @param data 输出订单统计报表
//     * @param type 1本周 2本月
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    ApiResult orderReport(Output<AppBillReportModel> data, Integer type) throws Exception;
//
//
//    /**
//     * 销售额统计报表
//     * <b>：罗国华</b>
//     *
//     * @param data 输出销售额统计报表
//     * @param type 1本周 2本月
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    ApiResult salesReport(Output<AppSalesReportModel> data, Integer type) throws Exception;
//
//
//    /**
//     * 会员量统计报表
//     * <b>：罗国华</b>
//     *
//     * @param data 输出会员量统计数据
//     * @param type 1本周 2本月
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    ApiResult userReport(Output<AppMemberReportModel> data, Integer type) throws Exception;

    /**
     * 订单统计报表
     *
     * @param totalAmount  订单总量
     * @param todayAmount  今日总量
     * @param weekAmount   本周总量
     * @param monthAmount  本月总量
     * @param todayTimes   今日时间数据
     * @param todayAmounts 今日数量数据
     * @param weekTimes    本周时间数据
     * @param weekAmounts  本周数量数据
     * @param monthTimes   本月时间数据
     * @param monthAmounts 本月数量数据
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult orderReport(Output<Long> totalAmount, Output<Long> todayAmount, Output<Long> weekAmount, Output<Long> monthAmount
            , Output<Integer[]> todayTimes, Output<Integer[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Integer[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Integer[]> monthAmounts) throws Exception;


    /**
     * 销售额统计报表
     *
     * @param totalAmount  销售总额
     * @param todayAmount  今日销售额
     * @param weekAmount   本周销售额
     * @param monthAmount  本月销售额
     * @param todayTimes   今日时间数据
     * @param todayAmounts 今日销售额数据
     * @param weekTimes    本周时间数据
     * @param weekAmounts  本周销售额数据
     * @param monthTimes   本月时间数据
     * @param monthAmounts 本月销售额数据
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult salesReport(Output<Long> totalAmount, Output<Float> todayAmount, Output<Float> weekAmount, Output<Float> monthAmount
            , Output<Integer[]> todayTimes, Output<Float[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Float[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Float[]> monthAmounts) throws Exception;


    /**
     * 会员量统计报表
     *
     * @param totalMember         会员总数
     * @param totalPartner        分销商总数
     * @param todayMemberAmount   今日会员量
     * @param weekMemberAmount    本周会员量
     * @param monthMemberAmount   本月会员量
     * @param todayMemberTimes    今日会员时间数据
     * @param todayMemberAmounts  今日会员量数据
     * @param weekMemberTimes     本周会员时间数据
     * @param weekMemberAmounts   本周会员量数据
     * @param monthMemberTimes    本月会员时间数据
     * @param monthMemberAmounts  本月会员量数据
     * @param todayPartnerAmount  今日分销商量
     * @param weekPartnerAmount   本周分销商量
     * @param monthPartnerAmount  本月分销商量
     * @param todayPartnerTimes   今日分销商时间数据
     * @param todayPartnerAmounts 今日分销商量数据
     * @param weekPartnerTimes    本周分销商时间数据
     * @param weekPartnerAmounts  本周分销商量数据
     * @param monthPartnerTimes   本月分销商时间数据
     * @param monthPartnerAmounts 本月分销商量数据
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult userReport(Output<Long> totalMember, Output<Long> totalPartner
            , Output<Long> todayMemberAmount, Output<Long> weekMemberAmount, Output<Long> monthMemberAmount
            , Output<Integer[]> todayMemberTimes, Output<Integer[]> todayMemberAmounts
            , Output<Date[]> weekMemberTimes, Output<Integer[]> weekMemberAmounts
            , Output<Date[]> monthMemberTimes, Output<Integer[]> monthMemberAmounts
            , Output<Long> todayPartnerAmount, Output<Long> weekPartnerAmount, Output<Long> monthPartnerAmount
            , Output<Integer[]> todayPartnerTimes, Output<Integer[]> todayPartnerAmounts
            , Output<Date[]> weekPartnerTimes, Output<Integer[]> weekPartnerAmounts
            , Output<Date[]> monthPartnerTimes, Output<Integer[]> monthPartnerAmounts
    ) throws Exception;

    /**
     * 其他信息统计
     *
     * @param otherInfoList
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult otherStatistics(Output<AppOtherInfoModel> otherInfoList) throws Exception;


    /**
     * 返利积分统计
     * top 20
     *
     * @param list
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult topScore(Output<AppTopScoreModel[]> list) throws Exception;


    /**
     * 消费统计
     * top 20
     *
     * @param list
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult topConsume(Output<AppTopConsumeModel[]> list) throws Exception;


    /**
     * 商品销售前10排行
     * 按照数量排序
     *
     * @param list
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult topSales(Output<AppTopSalesModel[]> list) throws Exception;
}
