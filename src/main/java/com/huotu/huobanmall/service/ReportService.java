package com.huotu.huobanmall.service;

import com.huotu.huobanmall.model.app.AppBillReportModel;
import com.huotu.huobanmall.model.app.AppMemberReportModel;
import com.huotu.huobanmall.model.app.AppSalesReportModel;

/**
 * Created by lgh on 2015/9/1.
 */
public interface ReportService {
    AppBillReportModel orderReport(Integer merchantId,Integer type) throws Exception;

    AppSalesReportModel salesReport(Integer merchantId,Integer type) throws Exception;

    AppMemberReportModel userReport(Integer merchantId,Integer type) throws Exception;
}
