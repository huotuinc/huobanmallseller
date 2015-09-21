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

    /**
     * 今日新增信息
     *
     * @param totalSales            总销售额
     * @param todaySales            今日销售额
     * @param orderHour             订单时间段数组
     * @param orderAmount           订单时间段值数组
     * @param memberHour            会员时间段数组
     * @param memberAmount          会员时间段值数组
     * @param partnerHour           小伙伴时间段数组
     * @param partnerAmount         小伙伴时间段值数组
     * @param todayMemberAmount     今日新增会员数
     * @param todayOrderAmount      今日新增订单数
     * @param todayPartnerAmount    今日新增小伙伴数
     * @return                      今日新增信息
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult newToday(Output<Float> totalSales, Output<Float> todaySales
            , Output<Integer[]> orderHour, Output<Integer[]> orderAmount
            , Output<Integer[]> memberHour, Output<Integer[]> memberAmount
            , Output<Integer[]> partnerHour, Output<Integer[]> partnerAmount
            , Output<Integer> todayOrderAmount
            , Output<Integer> todayMemberAmount
            , Output<Integer> todayPartnerAmount
    ) throws Exception;


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
    ApiResult salesReport(Output<Float> totalAmount, Output<Float> todayAmount, Output<Float> weekAmount, Output<Float> monthAmount
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
     * @param todayTimes          今日时间数据
     * @param todayMemberAmounts  今日会员量数据
     * @param weekTimes           本周时间数据
     * @param weekMemberAmounts   本周会员量数据
     * @param monthTimes          本月时间数据
     * @param monthMemberAmounts  本月会员量数据
     * @param todayPartnerAmount  今日分销商量
     * @param weekPartnerAmount   本周分销商量
     * @param monthPartnerAmount  本月分销商量
     * @param todayPartnerAmounts 今日分销商量数据
     * @param weekPartnerAmounts  本周分销商量数据
     * @param monthPartnerAmounts 本月分销商量数据
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult userReport(Output<Long> totalMember, Output<Long> totalPartner
            , Output<Long> todayMemberAmount, Output<Long> weekMemberAmount, Output<Long> monthMemberAmount
            , Output<Integer[]> todayTimes, Output<Integer[]> todayMemberAmounts
            , Output<Date[]> weekTimes, Output<Integer[]> weekMemberAmounts
            , Output<Date[]> monthTimes, Output<Integer[]> monthMemberAmounts
            , Output<Long> todayPartnerAmount, Output<Long> weekPartnerAmount, Output<Long> monthPartnerAmount
            , Output<Integer[]> todayPartnerAmounts
            , Output<Integer[]> weekPartnerAmounts
            , Output<Integer[]> monthPartnerAmounts
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
     *
     *
     * @param list
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult userScoreList(Output<AppTopScoreModel[]> list) throws Exception;


    /**
     * 消费统计
     *
     *
     * @param list
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult userConsumeList(Output<AppTopConsumeModel[]> list) throws Exception;


    /**
     * 商品销量前10排行
     * 按照数量排序
     *
     * @param list
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    ApiResult topSales(Output<AppTopSalesModel[]> list) throws Exception;
}
