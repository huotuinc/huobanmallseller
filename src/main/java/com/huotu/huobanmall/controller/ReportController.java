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

/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {

    @Autowired
    private ReportService reportService;

    @Override
    @RequestMapping("/orderReport")
    public ApiResult orderReport(Output<AppBillReportModel> data, Integer type) throws Exception {
        data.outputData(reportService.orderReport(PublicParameterHolder.getParameters().getCurrentUser().getId(), type));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/salesReport")
    public ApiResult salesReport(Output<AppSalesReportModel> data, Integer type) throws Exception {
        data.outputData(reportService.salesReport(PublicParameterHolder.getParameters().getCurrentUser().getId(),type));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/userReport")
    public ApiResult userReport(Output<AppMemberReportModel> data, Integer type) throws Exception {
        data.outputData(reportService.userReport(PublicParameterHolder.getParameters().getCurrentUser().getId(),type));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }
}
