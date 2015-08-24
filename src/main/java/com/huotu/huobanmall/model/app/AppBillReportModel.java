package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 订单统计数据
 * Created by Administrator on 2015/8/24.
 */
public class AppBillReportModel {

    /**
     * 本周
     */
    private Integer weekAmount;
    /**
     *  本月
     */
    private Integer monthAmount;

    /**
     * 本周或本月的数据
     */
    private List<AppBillReportListModel> list;
}
