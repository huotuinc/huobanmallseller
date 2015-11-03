/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.controller;

import com.huotu.common.DateHelper;
import com.huotu.common.MathHelper;
import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.*;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.repository.ProductRepository;
import com.huotu.huobanmall.repository.SellLogRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.*;


/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {
    static final int TOP_PAGE = 10;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    private CountService countService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RebateService rebateService;
    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private SellLogService sellLogService;

    @Autowired
    private SellLogRepository sellLogRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommonConfigService commonConfigService;

    @Override
    @RequestMapping("/newToday")
    public ApiResult newToday(Output<Double> totalSales,
                              Output<Double> todaySales,
                              Output<Integer[]> orderHour,
                              Output<Integer[]> orderAmount,
                              Output<Integer[]> memberHour,
                              Output<Integer[]> memberAmount,
                              Output<Integer[]> partnerHour,
                              Output<Integer[]> partnerAmount,
                              Output<Integer> todayOrderAmount,
                              Output<Integer> todayMemberAmount,
                              Output<Integer> todayPartnerAmount

    ) throws Exception {
        //获取当前商家信息
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        //获取时间段横坐标数组
        Integer[] hours = DateHelper.getTimeAbscissa();
        //计算今天各个时间段新增的订单数量

        //获取当日订单的map值
        Map<Integer, Integer> map = countService.todayOrder(merchant);

        Integer[] orders = DateHelper.getValueOrdinate();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            orders[(entry.getKey()-1)/3]+=entry.getValue();
        }

        //计算今天各个时间段新增的会员数量
        map = countService.todayMember(merchant);
        Integer[] members=DateHelper.getValueOrdinate();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            members[(entry.getKey()-1)/3] += entry.getValue();
        }
        //计算今天各个时间段新增的小伙伴数量
        map = countService.todayPartner(merchant);
        Integer[] partners = DateHelper.getValueOrdinate();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            partners[(entry.getKey()-1)/3] += entry.getValue();
        }

        //返回订单时间段数组
        orderHour.outputData(hours);
        //返回会员时间段数组
        memberHour.outputData(hours);
        //返回小伙伴时间段数组
        partnerHour.outputData(hours);
        //返回订单时间段值数组
        orderAmount.outputData(orders);
        //返回会员时间段值数组
        memberAmount.outputData(members);
        //返回小伙伴时间段值数组
        partnerAmount.outputData(partners);
        //返回今日销售额
        double countDodaySales = orderService.countSale(merchant);
        todaySales.outputData(MathHelper.retainDecimal(countDodaySales,2));
        //返回总销售额
        totalSales.outputData(MathHelper.retainDecimal(countService.getTotalSales(merchant)+countDodaySales,2));
        //返回今日新增订单数
        todayOrderAmount.outputData(orderService.countOrderQuantity(merchant));
        //返回今日新增会员数
        todayMemberAmount.outputData(userService.countTodayMember(merchant));
        //返回今日新增小伙伴数
        todayPartnerAmount.outputData(userService.countTodayPartner(merchant));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/orderReport")
    public ApiResult orderReport(Output<Long> totalAmount, Output<Long> todayAmount, Output<Long> weekAmount, Output<Long> monthAmount
            , Output<Integer[]> todayTimes, Output<Integer[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Integer[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Integer[]> monthAmounts) throws Exception {

        //获取当前公共信息
        AppPublicModel apm = PublicParameterHolder.getParameters();

        //获取时间段横坐标数组
        Integer[] hours = DateHelper.getTimeAbscissa();
        Integer[] values=DateHelper.getValueOrdinate();

        //统计今日订单总数
        Map<Integer, Integer> mapToday = countService.todayOrder(apm.getCurrentUser());

        for (Map.Entry<Integer, Integer> entry : mapToday.entrySet()) {
            values[(entry.getKey()-1)/3]+=entry.getValue();
        }

        todayTimes.outputData(hours);
        todayAmounts.outputData(values);

//        todayTimes.outputData(mapToday.keySet().toArray(new Integer[mapToday.keySet().size()]));
//        todayAmounts.outputData(mapToday.values().toArray(new Integer[mapToday.values().size()]));


        Long todayCountAmount = mapToday.values().stream().mapToInt(x -> x).summaryStatistics().getSum();
        todayAmount.outputData(todayCountAmount);

        //本周订单
        Map<Date, Integer> mapWeek = countService.getWeekOrder(apm.getCurrentUser());
        mapWeek.put(DateHelper.getThisDayBegin(), todayCountAmount.intValue());




        weekTimes.outputData(mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]));
        weekAmounts.outputData(mapWeek.values().toArray(new Integer[mapWeek.values().size()]));
        weekAmount.outputData(mapWeek.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        //本月订单
        Map<Date, Integer> mapMonth = countService.getMonthOrder(apm.getCurrentUser());
        mapMonth.put(DateHelper.getThisDayBegin(), todayCountAmount.intValue());
        //月转周
        mapMonth = MonthToWeek(mapMonth, Integer.class);
        monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
        monthAmounts.outputData(mapMonth.values().toArray(new Integer[mapMonth.values().size()]));
        monthAmount.outputData(mapMonth.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        totalAmount.outputData(countService.getTotalOrders(apm.getCurrentUser()));

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/salesReport")
    public ApiResult salesReport(
            Output<Double> totalAmount, Output<Double> todayAmount, Output<Double> weekAmount, Output<Double> monthAmount
            , Output<Integer[]> todayTimes, Output<Double[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Double[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Double[]> monthAmounts
    ) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();

        //今日销售数据
        Map<Integer, Double> mapToday = countService.getDaySales(apm.getCurrentUser());

        todayTimes.outputData(mapToday.keySet().toArray(new Integer[mapToday.keySet().size()]));
        todayAmounts.outputData(mapToday.values().toArray(new Double[mapToday.values().size()]));
        double todayCountSales = mapToday.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum();
        todayAmount.outputData(MathHelper.retainDecimal(todayCountSales,2));


        //周销售数据
        Map<Date, Double> mapWeek = countService.getWeekSales(apm.getCurrentUser());
        mapWeek.put(DateHelper.getThisDayBegin(), todayCountSales);
        weekTimes.outputData(mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]));
        weekAmounts.outputData(mapWeek.values().toArray(new Double[mapWeek.values().size()]));
        weekAmount.outputData(MathHelper.retainDecimal(mapWeek.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum(),2));


        //月销售数据
        Map<Date, Double> mapMonth = countService.getMonthSales(apm.getCurrentUser());
        mapMonth.put(DateHelper.getThisDayBegin(), todayCountSales);
        //月转周
        mapMonth = MonthToWeek(mapMonth, Double.class);

        monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
        monthAmounts.outputData(mapMonth.values().toArray(new Double[mapMonth.values().size()]));
        monthAmount.outputData(MathHelper.retainDecimal(mapMonth.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum(),2));

        totalAmount.outputData(MathHelper.retainDecimal(countService.getTotalSales(apm.getCurrentUser())+todayCountSales,2));

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/userReport")
    public ApiResult userReport(
            Output<Long> totalMember, Output<Long> totalPartner
            , Output<Long> todayMemberAmount, Output<Long> weekMemberAmount, Output<Long> monthMemberAmount
            , Output<Integer[]> todayTimes, Output<Integer[]> todayMemberAmounts
            , Output<Date[]> weekTimes, Output<Integer[]> weekMemberAmounts
            , Output<Date[]> monthTimes, Output<Integer[]> monthMemberAmounts
            , Output<Long> todayPartnerAmount, Output<Long> weekPartnerAmount, Output<Long> monthPartnerAmount
            , Output<Integer[]> todayPartnerAmounts
            , Output<Integer[]> weekPartnerAmounts
            , Output<Integer[]> monthPartnerAmounts
    ) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();
        //今日会员
        Map<Integer, Integer> mapTodayMember = countService.todayMember(apm.getCurrentUser());
        Long todayCountMemberAmount = mapTodayMember.values().stream().mapToInt(x -> x).summaryStatistics().getSum();
        todayMemberAmount.outputData(todayCountMemberAmount);

        //今日分销商
        Map<Integer, Integer> mapTodayPartner = countService.todayPartner(apm.getCurrentUser());
        Long todayCountPartnerAmount = mapTodayPartner.values().stream().mapToInt(x -> x).summaryStatistics().getSum();
        todayPartnerAmount.outputData(todayCountPartnerAmount);

        //合并今日会员和分销商
        List<Integer> listTodayTimes = new ArrayList<>();
        for (Integer hour : mapTodayMember.keySet()) {
            if (!listTodayTimes.contains(hour)) listTodayTimes.add(hour);
        }
        for (Integer hour : mapTodayPartner.keySet()) {
            if (!listTodayTimes.contains(hour)) listTodayTimes.add(hour);
        }
        Collections.sort(listTodayTimes);
        todayTimes.outputData(listTodayTimes.toArray(new Integer[listTodayTimes.size()]));

        List<Integer> listTodayMemberAmounts = new ArrayList<>();
        List<Integer> listTodayPartnerAmounts = new ArrayList<>();
        for (Integer hour : listTodayTimes) {
            if (mapTodayMember.get(hour) == null)
                listTodayMemberAmounts.add(0);
            else
                listTodayMemberAmounts.add(mapTodayMember.get(hour));

            if (mapTodayPartner.get(hour) == null)
                listTodayPartnerAmounts.add(0);
            else
                listTodayPartnerAmounts.add(mapTodayPartner.get(hour));
        }
        todayMemberAmounts.outputData(listTodayMemberAmounts.toArray(new Integer[listTodayMemberAmounts.size()]));
        todayPartnerAmounts.outputData(listTodayPartnerAmounts.toArray(new Integer[listTodayPartnerAmounts.size()]));


        //周会员量
        Map<Date, Integer> mapWeekMember = countService.getWeekMember(apm.getCurrentUser());
        mapWeekMember.put(DateHelper.getThisDayBegin(), todayCountMemberAmount.intValue());
        weekMemberAmount.outputData(mapWeekMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        //周分销商
        Map<Date, Integer> mapWeekPartner = countService.getWeekPartner(apm.getCurrentUser());
        mapWeekPartner.put(DateHelper.getThisDayBegin(), todayCountPartnerAmount.intValue());
        weekPartnerAmount.outputData(mapWeekPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        //合并周会员和分销商
        List<Date> listWeekTimes = new ArrayList<>();
        for (Date hour : mapWeekMember.keySet()) {
            if (!listWeekTimes.contains(hour)) listWeekTimes.add(hour);
        }
        for (Date hour : mapWeekPartner.keySet()) {
            if (!listWeekTimes.contains(hour)) listWeekTimes.add(hour);
        }
        Collections.sort(listWeekTimes);
        weekTimes.outputData(listWeekTimes.toArray(new Date[listWeekTimes.size()]));

        List<Integer> listWeekMemberAmounts = new ArrayList<>();
        List<Integer> listWeekPartnerAmounts = new ArrayList<>();
        for (Date hour : listWeekTimes) {
            if (mapWeekMember.get(hour) == null)
                listWeekMemberAmounts.add(0);
            else
                listWeekMemberAmounts.add(mapWeekMember.get(hour));

            if (mapWeekPartner.get(hour) == null)
                listWeekPartnerAmounts.add(0);
            else
                listWeekPartnerAmounts.add(mapWeekPartner.get(hour));
        }

        weekMemberAmounts.outputData(listWeekMemberAmounts.toArray(new Integer[listWeekMemberAmounts.size()]));
        weekPartnerAmounts.outputData(listWeekPartnerAmounts.toArray(new Integer[listWeekPartnerAmounts.size()]));


        //月会员量
        Map<Date, Integer> mapMonthMember = countService.getMonthMember(apm.getCurrentUser());
        mapMonthMember.put(DateHelper.getThisDayBegin(), todayCountMemberAmount.intValue());
        mapMonthMember = MonthToWeek(mapMonthMember, Integer.class);
        monthMemberAmount.outputData(mapMonthMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        //月分销商
        Map<Date, Integer> mapMonthPartner = countService.getMonthPartner(apm.getCurrentUser());
        mapMonthPartner.put(DateHelper.getThisDayBegin(), todayCountPartnerAmount.intValue());
        //月转周
        mapMonthPartner = MonthToWeek(mapMonthPartner, Integer.class);
        monthPartnerAmount.outputData(mapMonthPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        //合并周会员和分销商
        List<Date> listMonthTimes = new ArrayList<>();
        for (Date hour : mapMonthMember.keySet()) {
            if (!listMonthTimes.contains(hour)) listMonthTimes.add(hour);
        }
        for (Date hour : mapMonthPartner.keySet()) {
            if (!listMonthTimes.contains(hour)) listMonthTimes.add(hour);
        }
        Collections.sort(listMonthTimes);
        monthTimes.outputData(listMonthTimes.toArray(new Date[listMonthTimes.size()]));

        List<Integer> listMonthMemberAmounts = new ArrayList<>();
        List<Integer> listMonthPartnerAmounts = new ArrayList<>();
        for (Date hour : listMonthTimes) {
            if (mapMonthMember.get(hour) == null)
                listMonthMemberAmounts.add(0);
            else
                listMonthMemberAmounts.add(mapMonthMember.get(hour));

            if (mapMonthPartner.get(hour) == null)
                listMonthPartnerAmounts.add(0);
            else
                listMonthPartnerAmounts.add(mapMonthPartner.get(hour));
        }
        monthMemberAmounts.outputData(listMonthMemberAmounts.toArray(new Integer[listMonthMemberAmounts.size()]));
        monthPartnerAmounts.outputData(listMonthPartnerAmounts.toArray(new Integer[listMonthPartnerAmounts.size()]));

        //统计注册会员总数
        totalMember.outputData(countService.getTotalMembers(apm.getCurrentUser()));
        //统计分销商的总数
        totalPartner.outputData(countService.getTotalMembersType(apm.getCurrentUser(),1));

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    /**
     * 月转周
     * 以周末为分界点
     * 周数据为空的补上数据
     *
     * @param map
     * @return
     */
    private <T> Map<Date, T> MonthToWeek(Map<Date, T> map, Class<T> cls) throws ParseException {
        if (map.size() <= 0) return null;
        //只处理当月的数据
        Date curMonth = map.keySet().toArray(new Date[map.keySet().size()])[0];
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curMonth);

        //获取当前时间的本周的礼拜天
        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        c.add(Calendar.DAY_OF_YEAR,1);
        //获取当月每个周末
        List<Date> listWeekend = DateHelper.getMonthWeekEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        Map<Date, T> result = new TreeMap<>();
        listWeekend.forEach(x->{
            if(x.getTime()<=c.getTime().getTime()){
                if (Integer.class.equals(cls)) {
                    result.put(x, (T)(Integer) 0);
                }
                if (Float.class.equals(cls)) {
                    result.put(x, (T)(Float)0.0f);
                }
                if (Double.class.equals(cls)) {
                    result.put(x, (T)(Double)0.0);
                }

            }
        });

        //统计每个周末数据
        for (Date date : map.keySet()) {
            T value = map.get(date);
            for (Date weekend : listWeekend)
                if (date.getTime() <= weekend.getTime()) {
                    if (Integer.class.equals(cls)) {
                        Integer t = (result.get(weekend) == null ? 0 : (Integer) result.get(weekend)) + (Integer) value;
                        result.put(weekend, (T) t);
                        break;
                    }
                    if (Float.class.equals(cls)) {
                        Float t = (result.get(weekend) == null ? 0 : (Float) result.get(weekend)) + (Float) value;
                        result.put(weekend, (T) t);
                        break;
                    }
                    if (Double.class.equals(cls)) {
                        Double t = (result.get(weekend) == null ? 0 : (Double) result.get(weekend)) + (Double) value;
                        result.put(weekend, (T) t);
                        break;
                    }
                }
        }



        return result;
    }


    @Override
    @RequestMapping("/otherStatistics")
    public ApiResult otherStatistics(Output<AppOtherInfoModel> otherInfoList) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        AppOtherInfoModel appOtherInfoModel = new AppOtherInfoModel();
        appOtherInfoModel.setBillAmount(countService.getTotalOrders(merchant));
//                + countService.todayOrder(merchant).values().stream().mapToInt(x -> x).summaryStatistics().getSum());


        appOtherInfoModel.setGoodsAmount(goodsService.countByMerchant(merchant));


        appOtherInfoModel.setDiscributorAmount(countService.getTotalMembersType(merchant,1));
//                + countService.todayPartner(merchant).values().stream().mapToInt(x -> x).summaryStatistics().getSum());


        appOtherInfoModel.setMemberAmount(countService.getTotalMembersType(merchant,0));
//                + countService.todayMember(merchant).values().stream().mapToInt(x -> x).summaryStatistics().getSum());
        otherInfoList.outputData(appOtherInfoModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/topScore")
    public ApiResult topScore(Output<AppTopScoreModel[]> list) throws Exception {

        AppPublicModel apm = PublicParameterHolder.getParameters();
        List<AppTopScoreModel> list1 =  rebateService.topScore(apm.getCurrentUser());
        list.outputData(list1.toArray(new AppTopScoreModel[list1.size()]));
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/topSales")
    public ApiResult topSales(Output<AppTopSalesModel[]> list) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        List<Order> orderList = orderService.searchTopOrder(merchant, 1, new PageRequest(0, 10)).getContent();
        AppTopSalesModel[] appTopSalesModels = new AppTopSalesModel[orderList.size()];
        for (int i = 0; i < orderList.size(); i++) {
            AppTopSalesModel appTopSalesModel = new AppTopSalesModel();
            Order order = orderList.get(i);
            appTopSalesModel.setOrderNo(order.getId());
            appTopSalesModel.setMoney(order.getPrice());
            appTopSalesModel.setPictureUrl("");//todo 订单图片设置

            appTopSalesModels[i] = appTopSalesModel;
        }
        list.outputData(appTopSalesModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/topConsume")
    public ApiResult topConsume(Output<AppTopConsumeModel[]> list) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        List<Object[]> toplist = orderService.searchTopExpenditure(merchant, new PageRequest(0, TOP_PAGE)).getContent();
        AppTopConsumeModel[] appTopConsumeModels = new AppTopConsumeModel[toplist.size()];
        for (int i = 0; i < toplist.size(); i++) {
            AppTopConsumeModel appTopConsumeModel = new AppTopConsumeModel();
            Object[] objects = toplist.get(i);
            Integer userId = (Integer) objects[0];
            User user = userRepository.findOne(userId);
            double money = (Double) objects[1];
            long amount = (Long) objects[2];
            appTopConsumeModel.setPictureUrl(commonConfigService.getResoureServerUrl()+user.getUserFace());
            appTopConsumeModel.setName(userService.getViewUserName(user));
            appTopConsumeModel.setMoney(money);
            appTopConsumeModel.setAmount((int) amount);
            appTopConsumeModels[i] = appTopConsumeModel;
        }
        list.outputData(appTopConsumeModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


    @Override
    @RequestMapping("/topGoods")
    public ApiResult topGoods(Output<AppTopGoodsModel[]> list) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        List<Object[]> toplist = sellLogService.countTopGoodList(merchant);
        AppTopGoodsModel[] appTopGoodsModels = new AppTopGoodsModel[toplist.size()];
        for (int i = 0; i < toplist.size(); i++) {
            AppTopGoodsModel appTopGoodsModel = new AppTopGoodsModel();
            Object[] objects = toplist.get(i);



            String goodName = (String) objects[0];
//            Product product = productRepository.findOne(productId);
            long amount = (Long) objects[1];
            Integer goodId=(Integer)objects[2];
            appTopGoodsModel.setTitle(goodName);
            appTopGoodsModel.setAmount((int) amount);
//            appTopGoodsModel.setPrice(product.getPrice());
            appTopGoodsModel.setPicture(commonConfigService.getResoureServerUrl() + goodsRepository.findOne(goodId).getSmallPic());
            appTopGoodsModels[i] = appTopGoodsModel;
        }
        list.outputData(appTopGoodsModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


}
