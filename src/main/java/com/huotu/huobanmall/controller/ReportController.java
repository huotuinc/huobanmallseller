package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.model.app.AppBillReportModel;
import com.huotu.huobanmall.model.app.AppMemberReportModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.model.app.AppSalesReportModel;
import com.huotu.huobanmall.repository.CountDayOrderRepository;
import com.huotu.huobanmall.service.CountService;
import com.huotu.huobanmall.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.Map;

/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {

    @Autowired
    private CountService countService;

    @Override
    @RequestMapping("/orderReport")
    public ApiResult orderReport(Output<Long> todayAmount, Output<Long> weekAmount, Output<Long> monthAmount
            , Output<Integer[]> todayTimes, Output<Integer[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Integer[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Integer[]> monthAmounts) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();


        Map<Date, Integer> mapWeek = countService.getWeekOrder(apm.getCurrentUser());
        weekTimes.outputData((Date[]) mapWeek.keySet().toArray());
        weekAmounts.outputData((Integer[]) mapWeek.values().toArray());

        weekAmount.outputData(mapWeek.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        Map<Date, Integer> mapMonth = countService.getMonthOrder(apm.getCurrentUser());
        monthTimes.outputData((Date[]) mapMonth.keySet().toArray());
        monthAmounts.outputData((Integer[]) mapMonth.values().toArray());

        monthAmount.outputData(mapMonth.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/salesReport")
    public ApiResult salesReport(
            Output<Float> todayAmount, Output<Float> weekAmount, Output<Float> monthAmount
            , Output<Integer[]> todayTimes, Output<Float[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Float[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Float[]> monthAmounts
    ) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();

        Map<Integer, Float> mapToday = countService.getDaySales(apm.getCurrentUser());
        todayTimes.outputData((Integer[]) mapToday.keySet().toArray());
        todayAmounts.outputData((Float[]) mapToday.values().toArray());
        todayAmount.outputData(((Double) mapToday.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());


        Map<Date, Float> mapWeek = countService.getWeekSales(apm.getCurrentUser());
        weekTimes.outputData((Date[]) mapWeek.keySet().toArray());
        weekAmounts.outputData((Float[]) mapWeek.values().toArray());

        weekAmount.outputData(((Double) mapWeek.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());

        Map<Date, Float> mapMonth = countService.getMonthSales(apm.getCurrentUser());
        monthTimes.outputData((Date[]) mapMonth.keySet().toArray());
        monthAmounts.outputData((Float[]) mapMonth.values().toArray());

        monthAmount.outputData(((Double) mapMonth.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/userReport")
    public ApiResult userReport(
            Output<Long> total, Output<Long> todayMemberAmount, Output<Long> weekMemberAmount, Output<Long> monthMemberAmount
            , Output<Integer[]> todayMemberTimes, Output<Integer[]> todayMemberAmounts
            , Output<Date[]> weekMemberTimes, Output<Integer[]> weekMemberAmounts
            , Output<Date[]> monthMemberTimes, Output<Integer[]> monthMemberAmounts
            , Output<Long> todayPartnerAmount, Output<Long> weekPartnerAmount, Output<Long> monthPartnerAmount
            , Output<Integer[]> todayPartnerTimes, Output<Integer[]> todayPartnerAmounts
            , Output<Date[]> weekPartnerTimes, Output<Integer[]> weekPartnerAmounts
            , Output<Date[]> monthPartnerTimes, Output<Integer[]> monthPartnerAmounts
    ) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();

        Map<Date, Integer> mapWeekMember = countService.getWeekMember(apm.getCurrentUser());
        weekMemberTimes.outputData((Date[]) mapWeekMember.keySet().toArray());
        weekMemberAmounts.outputData((Integer[]) mapWeekMember.values().toArray());

        weekMemberAmount.outputData(mapWeekMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        Map<Date, Integer> mapMonthMember = countService.getMonthMember(apm.getCurrentUser());
        monthMemberTimes.outputData((Date[]) mapMonthMember.keySet().toArray());
        monthMemberAmounts.outputData((Integer[]) mapMonthMember.values().toArray());

        monthMemberAmount.outputData(mapMonthMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        Map<Date, Integer> mapWeekPartner = countService.getWeekPartner(apm.getCurrentUser());
        weekPartnerTimes.outputData((Date[]) mapWeekPartner.keySet().toArray());
        weekPartnerAmounts.outputData((Integer[]) mapWeekPartner.values().toArray());

        weekPartnerAmount.outputData(mapWeekPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        Map<Date, Integer> mapMonthPartner = countService.getMonthPartner(apm.getCurrentUser());
        monthPartnerTimes.outputData((Date[]) mapMonthPartner.keySet().toArray());
        monthPartnerAmounts.outputData((Integer[]) mapMonthPartner.values().toArray());

        monthPartnerAmount.outputData(mapMonthPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

//    @Autowired
//    private ReportService reportService;
//
//    @Override
//    @RequestMapping("/orderReport")
//    public ApiResult orderReport(Output<AppBillReportModel> data, Integer type) throws Exception {
//        data.outputData(reportService.orderReport(PublicParameterHolder.getParameters().getCurrentUser().getId(), type));
//        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
//    }
//
//    @Override
//    @RequestMapping("/salesReport")
//    public ApiResult salesReport(Output<AppSalesReportModel> data, Integer type) throws Exception {
//        data.outputData(reportService.salesReport(PublicParameterHolder.getParameters().getCurrentUser().getId(),type));
//        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
//    }
//
//    @Override
//    @RequestMapping("/userReport")
//    public ApiResult userReport(Output<AppMemberReportModel> data, Integer type) throws Exception {
//        data.outputData(reportService.userReport(PublicParameterHolder.getParameters().getCurrentUser().getId(),type));
//        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
//    }
}
