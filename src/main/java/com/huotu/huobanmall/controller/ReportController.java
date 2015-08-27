package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.model.app.AppBillReportModel;
import com.huotu.huobanmall.model.app.AppMemberReportModel;
import com.huotu.huobanmall.model.app.AppSalesReportModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {
    @Override
    @RequestMapping("/orderReport")
    public ApiResult orderReport(Output<AppBillReportModel> data, Integer type) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/salesReport")
    public ApiResult salesReport(Output<AppSalesReportModel> data, Integer type) throws Exception {
        return null;
    }

    @Override
    @RequestMapping("/userReport")
    public ApiResult userReport(Output<AppMemberReportModel> member) throws Exception {
        return null;
    }
}
