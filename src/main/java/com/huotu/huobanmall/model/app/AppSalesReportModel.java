package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public class AppSalesReportModel {
    /**
     * 本周
     */
    private Integer weekAmount;
    /**
     *  本月
     */
    private Integer monthAmount;

    /**
     * 本周或本月销售额统计数据
     */
    private List<AppSalesReportListModel> list;
}
