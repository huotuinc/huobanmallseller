package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.model.app.AppBillReportModel;
import com.huotu.huobanmall.model.app.AppMemberReportModel;
import com.huotu.huobanmall.model.app.AppSalesReportModel;
import com.huotu.huobanmall.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {
    @Override
    @RequestMapping("/orderReport")
    public ApiResult orderReport(Output<Integer> todayAmount, Output<Integer> weekAmount, Output<Integer> monthAmount, Output<Integer[]> todayTimes, Output<Integer[]> todayAmounts, Output<Date[]> weekTimes, Output<Integer[]> weekAmounts, Output<Date[]> monthTimes, Output<Integer[]> monthAmounts) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/salesReport")
    public ApiResult salesReport(Output<Integer> todayAmount, Output<Integer> weekAmount, Output<Integer> monthAmount, Output<Integer[]> todayTimes, Output<Float[]> todayAmounts, Output<Date[]> weekTimes, Output<Float[]> weekAmounts, Output<Date[]> monthTimes, Output<Float[]> monthAmounts) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/userReport")
    public ApiResult userReport(Output<Integer> total, Output<Integer> todayMemberAmount, Output<Integer> weekMemberAmount, Output<Integer> monthMemberAmount, Output<Integer[]> todayMemberTimes, Output<Integer[]> todayMemberAmounts, Output<Date[]> weekMemberTimes, Output<Integer[]> weekMemberAmounts, Output<Date[]> monthMemberTimes, Output<Integer[]> monthMemberAmounts, Output<Integer> todayParnterAmount, Output<Integer> weekParnterAmount, Output<Integer> monthParnterAmount, Output<Integer[]> todayParnterTimes, Output<Integer[]> todayParnterAmounts, Output<Date[]> weekParnterTimes, Output<Integer[]> weekParnterAmounts, Output<Date[]> monthParnterTimes, Output<Integer[]> monthParnterAmounts) throws Exception {
        return null;
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
