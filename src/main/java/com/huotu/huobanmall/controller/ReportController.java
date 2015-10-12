package com.huotu.huobanmall.controller;

import com.huotu.common.DateHelper;
import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.entity.Product;
import com.huotu.huobanmall.entity.User;
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

    @Override
    @RequestMapping("/newToday")
    public ApiResult newToday(Output<Float> totalSales,
                              Output<Float> todaySales,
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
        //将Map结果分解成两个时间和数量的数组
        //计算今天各个时间段新增的订单数量

        Map<Integer, Integer> map = countService.todayOrder(merchant);
        Integer[] hoursOrder = new Integer[map.size()];
        Integer[] orders = new Integer[map.size()];
        int n = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursOrder[n] = (entry.getKey());
            orders[n] = entry.getValue();
            n++;
        }

        //计算今天各个时间段新增的会员数量
        n = 0;
        map = countService.todayMember(merchant);
        Integer[] hoursMember = new Integer[map.size()];
        Integer[] members = new Integer[map.size()];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursMember[n] = (entry.getKey());
            members[n] = entry.getValue();
            n++;
        }
        //计算今天各个时间段新增的小伙伴数量
        n = 0;
        map = countService.todayPartner(merchant);
        Integer[] hoursPartner = new Integer[map.size()];
        Integer[] partners = new Integer[map.size()];
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            hoursPartner[n] = (entry.getKey());
            partners[n] = entry.getValue();
            n++;
        }

        //返回订单时间段数组
        orderHour.outputData(hoursOrder);
        //返回会员时间段数组
        memberHour.outputData(hoursMember);
        //返回小伙伴时间段数组
        partnerHour.outputData(hoursPartner);
        //返回订单时间段值数组
        orderAmount.outputData(orders);
        //返回会员时间段值数组
        memberAmount.outputData(members);
        //返回小伙伴时间段值数组
        partnerAmount.outputData(partners);
        //返回今日销售额
        float countDodaySales = orderService.countSale(merchant);
        todaySales.outputData(countDodaySales);
        //返回总销售额
        totalSales.outputData(countService.getTotalSales(merchant));
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

        //统计今日订单总数
        Map<Integer, Integer> mapToday = countService.todayOrder(apm.getCurrentUser());
        todayTimes.outputData(mapToday.keySet().toArray(new Integer[mapToday.keySet().size()]));
        todayAmounts.outputData(mapToday.values().toArray(new Integer[mapToday.values().size()]));
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
            Output<Float> totalAmount, Output<Float> todayAmount, Output<Float> weekAmount, Output<Float> monthAmount
            , Output<Integer[]> todayTimes, Output<Float[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Float[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Float[]> monthAmounts
    ) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();

        //今日销售数据
        Map<Integer, Float> mapToday = countService.getDaySales(apm.getCurrentUser());

        todayTimes.outputData(mapToday.keySet().toArray(new Integer[mapToday.keySet().size()]));
        todayAmounts.outputData(mapToday.values().toArray(new Float[mapToday.values().size()]));
        float todayCountSales = ((Double) mapToday.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue();
        todayAmount.outputData(todayCountSales);


        //周销售数据
        Map<Date, Float> mapWeek = countService.getWeekSales(apm.getCurrentUser());
        mapWeek.put(DateHelper.getThisDayBegin(), todayCountSales);
        weekTimes.outputData(mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]));
        weekAmounts.outputData(mapWeek.values().toArray(new Float[mapWeek.values().size()]));
        weekAmount.outputData(((Double) mapWeek.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());


        //月销售数据
        Map<Date, Float> mapMonth = countService.getMonthSales(apm.getCurrentUser());
        mapMonth.put(DateHelper.getThisDayBegin(), todayCountSales);
        //月转周
        mapMonth = MonthToWeek(mapMonth, Float.class);
        monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
        monthAmounts.outputData(mapMonth.values().toArray(new Float[mapMonth.values().size()]));
        monthAmount.outputData(((Double) mapMonth.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());

        totalAmount.outputData(countService.getTotalSales(apm.getCurrentUser()));

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

        //获取当月每个周末
        List<Date> listWeekend = DateHelper.getMonthWeekEnd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        Map<Date, T> result = new TreeMap<>();
//        for (Date date : result.keySet()) {
//            result.put(date,(T)0 );
//        }

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
        List<AppTopScoreModel> list1 =  rebateService.topScore(apm.getCurrentUser(), 1);
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
            appTopConsumeModel.setPictureUrl(user.getUserFace());
            appTopConsumeModel.setName(userService.getViewUserName(user));
            appTopConsumeModel.setMoney((float) money);
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
        List<Object[]> toplist = sellLogService.countTopGoodList(merchant, new PageRequest(0, TOP_PAGE)).getContent();
        AppTopGoodsModel[] appTopGoodsModels = new AppTopGoodsModel[toplist.size()];
        for (int i = 0; i < toplist.size(); i++) {
            AppTopGoodsModel appTopGoodsModel = new AppTopGoodsModel();
            Object[] objects = toplist.get(i);
            Integer productId = (Integer) objects[0];
            Product product = productRepository.findOne(productId);
            long amount = (Long) objects[1];
            appTopGoodsModel.setTitle(product.getName());
            appTopGoodsModel.setAmount((int) amount);
            appTopGoodsModel.setPrice(product.getPrice());
            appTopGoodsModel.setPicture(product.getGoods().getPictureUrl());
            appTopGoodsModels[i] = appTopGoodsModel;
        }
        list.outputData(appTopGoodsModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }


}
