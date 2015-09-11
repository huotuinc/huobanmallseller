package com.huotu.huobanmall.controller;

import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppOtherInfoModel;
import com.huotu.huobanmall.model.app.AppPublicModel;
import com.huotu.huobanmall.service.CountService;
import com.huotu.huobanmall.service.GoodsService;
import com.huotu.huobanmall.service.OrderService;
import com.huotu.huobanmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {

    @Autowired
    private CountService countService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Override
    @RequestMapping("/orderReport")
    public ApiResult orderReport(Output<Long> todayAmount, Output<Long> weekAmount, Output<Long> monthAmount
            , Output<Integer[]> todayTimes, Output<Integer[]> todayAmounts
            , Output<Date[]> weekTimes, Output<Integer[]> weekAmounts
            , Output<Date[]> monthTimes, Output<Integer[]> monthAmounts) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        int nowHour = date.get(Calendar.HOUR_OF_DAY);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MINUTE, 0);
        //今天
        Date today = date.getTime();
        //统计今日订单总数
        todayAmount.outputData((long) orderService.countOrderQuantity(apm.getCurrentUser(), today));
        //统计今日详细数据
        Integer[] hoursOrder = new Integer[(nowHour + 2) / 3];
        Integer[] orders = new Integer[(nowHour + 2) / 3];
        int n = 0;
        Map<Integer, Integer> mapToday = countService.todayOrder(apm.getCurrentUser());
        for (Map.Entry<Integer, Integer> entry : mapToday.entrySet()) {
            hoursOrder[n] = (entry.getKey() + 1) * 3;
            orders[n] = entry.getValue();
            n++;
        }
        todayAmounts.outputData(orders);
        todayTimes.outputData(hoursOrder);


        Map<Date, Integer> mapWeek = countService.getWeekOrder(apm.getCurrentUser());
        if (mapWeek.size() > 0) {
            weekTimes.outputData(mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]));
            weekAmounts.outputData(mapWeek.values().toArray(new Integer[mapWeek.values().size()]));
            weekAmount.outputData(mapWeek.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());
        }

        weekAmount.outputData(mapWeek.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        Map<Date, Integer> mapMonth = countService.getMonthOrder(apm.getCurrentUser());
        if (mapMonth.size() > 0) {
            monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
            monthAmounts.outputData(mapMonth.values().toArray(new Integer[mapMonth.values().size()]));
            monthAmount.outputData(mapMonth.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());
        }

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
        if (mapToday.size() > 0) {
            todayTimes.outputData(mapToday.keySet().toArray(new Integer[mapToday.keySet().size()]));
            todayAmounts.outputData(mapToday.values().toArray(new Float[mapToday.values().size()]));
            todayAmount.outputData(((Double) mapToday.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());
        }


        Map<Date, Float> mapWeek = countService.getWeekSales(apm.getCurrentUser());
        if (mapWeek.size() > 0) {
            weekTimes.outputData(mapWeek.keySet().toArray(new Date[mapWeek.keySet().size()]));
            weekAmounts.outputData(mapWeek.values().toArray(new Float[mapWeek.values().size()]));
            weekAmount.outputData(((Double) mapWeek.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());
        }

        weekAmount.outputData(((Double) mapWeek.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());

        Map<Date, Float> mapMonth = countService.getMonthSales(apm.getCurrentUser());
        if (mapMonth.size() > 0) {
            monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
            monthAmounts.outputData(mapMonth.values().toArray(new Float[mapMonth.values().size()]));
            monthAmount.outputData(((Double) mapMonth.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());
        }

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
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        int nowHour = date.get(Calendar.HOUR_OF_DAY);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MINUTE, 0);
        //今天
        Date today = date.getTime();
        //统计注册会员总数
        total.outputData(userService.countAllMember(apm.getCurrentUser()));
        //统计今日新增会员总数
        todayMemberAmount.outputData((long) userService.countUserNumber(apm.getCurrentUser(), 0, today));
        //统计今日新增小伙伴总数
        todayPartnerAmount.outputData((long) userService.countUserNumber(apm.getCurrentUser(), 1, today));
        //统计今日会员新增明细
        Integer[] hoursMember = new Integer[(nowHour + 2) / 3];
        Integer[] members = new Integer[(nowHour + 2) / 3];
        Map<Integer, Integer> mapTodayMember = countService.todayMember(apm.getCurrentUser());
        int n = 0;
        for (Map.Entry<Integer, Integer> entry : mapTodayMember.entrySet()) {
            hoursMember[n] = (entry.getKey() + 1) * 3;
            members[n] = entry.getValue();
            n++;
        }
        todayMemberTimes.outputData(hoursMember);
        todayMemberAmounts.outputData(members);

        //统计今日小伙伴新增明细
        Integer[] hoursPartner = new Integer[(nowHour + 2) / 3];
        Integer[] partners = new Integer[(nowHour + 2) / 3];
        Map<Integer, Integer> mapTodayPartner = countService.todayPartner(apm.getCurrentUser());
        n = 0;
        for (Map.Entry<Integer, Integer> entry : mapTodayPartner.entrySet()) {
            hoursPartner[n] = (entry.getKey() + 1) * 3;
            partners[n] = entry.getValue();
            n++;
        }
        todayPartnerTimes.outputData(hoursPartner);
        todayPartnerAmounts.outputData(partners);


        Map<Date, Integer> mapWeekMember = countService.getWeekMember(apm.getCurrentUser());
        if (mapWeekMember.size() > 0) {
            weekMemberTimes.outputData(mapWeekMember.keySet().toArray(new Date[mapWeekMember.keySet().size()]));
            weekMemberAmounts.outputData(mapWeekMember.values().toArray(new Integer[mapWeekMember.values().size()]));
            weekMemberAmount.outputData(mapWeekMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());
        }

        weekMemberAmount.outputData(mapWeekMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        Map<Date, Integer> mapMonthMember = countService.getMonthMember(apm.getCurrentUser());
        if (mapMonthMember.size() > 0) {
            monthMemberTimes.outputData(mapMonthMember.keySet().toArray(new Date[mapMonthMember.keySet().size()]));
            monthMemberAmounts.outputData(mapMonthMember.values().toArray(new Integer[mapMonthMember.values().size()]));
            monthMemberAmount.outputData(mapMonthMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());
        }

        monthMemberAmount.outputData(mapMonthMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        Map<Date, Integer> mapWeekPartner = countService.getWeekPartner(apm.getCurrentUser());
        if (mapWeekPartner.size() > 0) {
            weekPartnerTimes.outputData(mapWeekPartner.keySet().toArray(new Date[mapWeekPartner.keySet().size()]));
            weekPartnerAmounts.outputData(mapWeekPartner.values().toArray(new Integer[mapWeekPartner.values().size()]));
            weekPartnerAmount.outputData(mapWeekPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());
        }

        weekPartnerAmount.outputData(mapWeekPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        Map<Date, Integer> mapMonthPartner = countService.getMonthPartner(apm.getCurrentUser());
        if (mapMonthPartner.size() > 0) {
            monthPartnerTimes.outputData(mapMonthPartner.keySet().toArray(new Date[mapMonthPartner.keySet().size()]));
            monthPartnerAmounts.outputData(mapMonthPartner.values().toArray(new Integer[mapMonthPartner.values().size()]));
            monthPartnerAmount.outputData(mapMonthPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());
        }

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/otherStatistics")
    public ApiResult otherStatistics(Output<AppOtherInfoModel> otherInfoList) throws Exception {
        Merchant merchant=PublicParameterHolder.getParameters().getCurrentUser();
        AppOtherInfoModel appOtherInfoModel=new AppOtherInfoModel();
        appOtherInfoModel.setBillAmount(orderService.countOrderQuantity(merchant));
        appOtherInfoModel.setGoodsAmount(goodsService.countByMerchant(merchant));
        appOtherInfoModel.setDiscributorAmount(userService.countUserNumber(merchant,1));
        appOtherInfoModel.setMemberAmount(userService.countUserNumber(merchant,0));
        otherInfoList.outputData(appOtherInfoModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

}
