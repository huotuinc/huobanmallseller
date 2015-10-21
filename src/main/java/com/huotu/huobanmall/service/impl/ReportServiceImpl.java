/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service.impl;

import com.huotu.common.DateHelper;
import com.huotu.common.StringHelper;
import com.huotu.huobanmall.model.app.*;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/1.
 */

@Service
public class ReportServiceImpl implements ReportService {


//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Override
//    public AppBillReportModel orderReport(Integer merchantId, Integer type) throws Exception {
//
//        Date startWeek = DateHelper.getThisWeekBegin();
//        Date startMonth = DateHelper.getThisMonthBegin();
//
//        AppBillReportModel model = new AppBillReportModel();
//        int weekAmount = orderRepository.countByWeekOrMonth(startWeek);
//        int monthAmount = orderRepository.countByWeekOrMonth(startMonth);
//
//        model.setWeekAmount(weekAmount);
//        model.setMonthAmount(monthAmount);
//        //本周
//        if (type == 1) {
//            model.setListBill(getOrderBillReport(merchantId, startWeek));
//            model.setListBuy(getOrderBuyReport(merchantId, startWeek));
//        } else if (type == 2) {
//            model.setListBill(getOrderBillReport(merchantId, startMonth));
//            model.setListBuy(getOrderBuyReport(merchantId, startMonth));
//        }
//        return model;
//    }
//
//    private List<AppBillReportListModel> getOrderBillReport(Integer merchantId, Date startTime) {
//        List<AppBillReportListModel> listBill = new ArrayList<>();
//        StringBuilder hql = new StringBuilder();
//        hql.append("select FUNC('DATE_FORMAT',order.time,'%Y-%m-%d') time,count(order) number " +
//                " from Order order where order.merchant.id=:merchantId and order.time>:time group by time order by time");
//        List list = orderRepository.queryHql(hql.toString(), query -> {
//            query.setParameter("merchantId", merchantId);
//            query.setParameter("time", startTime);
//        });
//
//        list.forEach(object -> {
//            Object[] objects = (Object[]) object;
//            AppBillReportListModel appBillReportListModel = new AppBillReportListModel();
//            appBillReportListModel.setTime((StringHelper.strToDate(objects[0].toString(), "yyyy-MM-dd")));
//            appBillReportListModel.setAmount(Integer.parseInt(objects[1].toString()));
//            listBill.add(appBillReportListModel);
//        });
//        return listBill;
//    }
//
//
//    private List<AppTopBuyAmountModel> getOrderBuyReport(Integer merchantId, Date startTime) {
//        List<AppTopBuyAmountModel> listBuy = new ArrayList<>();
//        StringBuilder hql = new StringBuilder();
//        hql.append("select order.productId,order.productTitle,count(order) number " +
//                " from OrderItems items where items.merchant.id=:merchantId and items.time>:time group by items.productId,order.productTitle order by number");
//        List list = orderRepository.queryHql(hql.toString(), query -> {
//            query.setParameter("merchantId", merchantId);
//            query.setParameter("time", startTime);
//        });
//
//        list.forEach(object -> {
//            Object[] objects = (Object[]) object;
//            AppTopBuyAmountModel appTopBuyAmountModel = new AppTopBuyAmountModel();
//            appTopBuyAmountModel.setTitle(objects[1].toString());
//            appTopBuyAmountModel.setAmount(Integer.parseInt(objects[2].toString()));
//            listBuy.add(appTopBuyAmountModel);
//        });
//
//        return listBuy;
//    }
//
//    @Override
//    public AppSalesReportModel salesReport(Integer merchantId, Integer type) throws Exception {
//        return null;
//    }
//
//    @Override
//    public AppMemberReportModel userReport(Integer merchantId, Integer type) throws Exception {
//        return null;
//    }
}
