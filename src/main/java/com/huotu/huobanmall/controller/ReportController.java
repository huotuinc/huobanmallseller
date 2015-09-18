package com.huotu.huobanmall.controller;

import com.huotu.common.DateHelper;
import com.huotu.huobanmall.api.ReportSystem;
import com.huotu.huobanmall.api.common.ApiResult;
import com.huotu.huobanmall.api.common.Output;
import com.huotu.huobanmall.api.common.PublicParameterHolder;
import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.Goods;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.model.app.*;
import com.huotu.huobanmall.repository.GoodsRepository;
import com.huotu.huobanmall.repository.UserRepository;
import com.huotu.huobanmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lgh on 2015/8/27.
 */

@Controller
@RequestMapping("/app")
public class ReportController implements ReportSystem {
    static final int TOP_PAGE = 20;

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
        monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
        monthAmounts.outputData(mapMonth.values().toArray(new Integer[mapMonth.values().size()]));
        monthAmount.outputData(mapMonth.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        totalAmount.outputData(countService.getTotalOrders(apm.getCurrentUser()) + todayCountAmount);

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
        monthTimes.outputData(mapMonth.keySet().toArray(new Date[mapMonth.keySet().size()]));
        monthAmounts.outputData(mapMonth.values().toArray(new Float[mapMonth.values().size()]));
        monthAmount.outputData(((Double) mapMonth.values().stream().mapToDouble((x) -> x).summaryStatistics().getSum()).floatValue());

          totalAmount.outputData(countService.getTotalSales(apm.getCurrentUser()));//todo

        totalAmount.outputData(countService.getTotalSales(apm.getCurrentUser()) + todayCountSales);

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/userReport")
    public ApiResult userReport(
            Output<Long> totalMember, Output<Long> totalPartner
            , Output<Long> todayMemberAmount, Output<Long> weekMemberAmount, Output<Long> monthMemberAmount
            , Output<Integer[]> todayMemberTimes, Output<Integer[]> todayMemberAmounts
            , Output<Date[]> weekMemberTimes, Output<Integer[]> weekMemberAmounts
            , Output<Date[]> monthMemberTimes, Output<Integer[]> monthMemberAmounts
            , Output<Long> todayPartnerAmount, Output<Long> weekPartnerAmount, Output<Long> monthPartnerAmount
            , Output<Integer[]> todayPartnerTimes, Output<Integer[]> todayPartnerAmounts
            , Output<Date[]> weekPartnerTimes, Output<Integer[]> weekPartnerAmounts
            , Output<Date[]> monthPartnerTimes, Output<Integer[]> monthPartnerAmounts
    ) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();
        //今日会员
        Map<Integer, Integer> mapTodayMember = countService.todayMember(apm.getCurrentUser());
        todayMemberTimes.outputData(mapTodayMember.keySet().toArray(new Integer[mapTodayMember.keySet().size()]));
        todayMemberAmounts.outputData(mapTodayMember.values().toArray(new Integer[mapTodayMember.values().size()]));
        Long todayCountMemberAmount = mapTodayMember.values().stream().mapToInt(x -> x).summaryStatistics().getSum();
        todayMemberAmount.outputData(todayCountMemberAmount);

        //今日分销商
        Map<Integer, Integer> mapTodayPartner = countService.todayPartner(apm.getCurrentUser());
        todayPartnerTimes.outputData(mapTodayPartner.keySet().toArray(new Integer[mapTodayPartner.keySet().size()]));
        todayPartnerAmounts.outputData(mapTodayPartner.values().toArray(new Integer[mapTodayPartner.values().size()]));
        Long todayCountPartnerAmount = mapTodayPartner.values().stream().mapToInt(x -> x).summaryStatistics().getSum();
        todayPartnerAmount.outputData(todayCountPartnerAmount);

        //周会员量
        Map<Date, Integer> mapWeekMember = countService.getWeekMember(apm.getCurrentUser());
        mapWeekMember.put(DateHelper.getThisDayBegin(), todayCountMemberAmount.intValue());
        weekMemberTimes.outputData(mapWeekMember.keySet().toArray(new Date[mapWeekMember.keySet().size()]));
        weekMemberAmounts.outputData(mapWeekMember.values().toArray(new Integer[mapWeekMember.values().size()]));
        weekMemberAmount.outputData(mapWeekMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        //月会员量
        Map<Date, Integer> mapMonthMember = countService.getMonthMember(apm.getCurrentUser());
        mapMonthMember.put(DateHelper.getThisDayBegin(), todayCountMemberAmount.intValue());
        monthMemberTimes.outputData(mapMonthMember.keySet().toArray(new Date[mapMonthMember.keySet().size()]));
        monthMemberAmounts.outputData(mapMonthMember.values().toArray(new Integer[mapMonthMember.values().size()]));
        monthMemberAmount.outputData(mapMonthMember.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        //周分销商
        Map<Date, Integer> mapWeekPartner = countService.getWeekPartner(apm.getCurrentUser());
        mapWeekPartner.put(DateHelper.getThisDayBegin(), todayCountPartnerAmount.intValue());
        weekPartnerTimes.outputData(mapWeekPartner.keySet().toArray(new Date[mapWeekPartner.keySet().size()]));
        weekPartnerAmounts.outputData(mapWeekPartner.values().toArray(new Integer[mapWeekPartner.values().size()]));
        weekPartnerAmount.outputData(mapWeekPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());

        //月分销商
        Map<Date, Integer> mapMonthPartner = countService.getMonthPartner(apm.getCurrentUser());
        mapMonthPartner.put(DateHelper.getThisDayBegin(), todayCountPartnerAmount.intValue());
        monthPartnerTimes.outputData(mapMonthPartner.keySet().toArray(new Date[mapMonthPartner.keySet().size()]));
        monthPartnerAmounts.outputData(mapMonthPartner.values().toArray(new Integer[mapMonthPartner.values().size()]));
        monthPartnerAmount.outputData(mapMonthPartner.values().stream().mapToInt((x) -> x).summaryStatistics().getSum());


        //统计注册会员总数
        totalMember.outputData(countService.getTotalMembers(apm.getCurrentUser()) + todayCountMemberAmount);
        //统计分销商的总数
        totalPartner.outputData(countService.getTotalPartner(apm.getCurrentUser()) + todayCountPartnerAmount);

        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/topScore")
    public ApiResult topScore(Output<AppTopScoreModel[]> list) throws Exception {
        AppPublicModel apm = PublicParameterHolder.getParameters();
        List<Rebate> rebates = rebateService.showTopScore(apm.getCurrentUser(), 1).getContent();
        AppTopScoreModel[] appTopScoreModels = new AppTopScoreModel[rebates.size()];
        for (int i = 0; i < rebates.size(); i++) {
            AppTopScoreModel appTopScoreModel = new AppTopScoreModel();
            Rebate rebate = rebates.get(i);
            User user = userRepository.findOne(rebate.getId());
            appTopScoreModel.setMobile(user.getMobile());
            appTopScoreModel.setName(user.getUsername());
            appTopScoreModel.setNickName(user.getNickname());
            appTopScoreModel.setScore(rebate.getScore());
            appTopScoreModel.setPictureUrl(user.getUserFace());  //todo 图片路径需要修改
            appTopScoreModels[i] = appTopScoreModel;
        }
        list.outputData(appTopScoreModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/topConsume")
    public ApiResult topConsume(Output<AppTopConsumeModel[]> list) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        List<Object[]> toplist = orderService.countUserExpenditureList(merchant, new PageRequest(0, TOP_PAGE)).getContent();
        AppTopConsumeModel[] appTopConsumeModels = new AppTopConsumeModel[toplist.size()];
        for (int i = 0; i < toplist.size(); i++) {
            AppTopConsumeModel appTopConsumeModel = new AppTopConsumeModel();
            Object[] objects = toplist.get(i);
            Integer userId = (Integer) objects[0];
            User user = userRepository.findOne(userId);
            double money = (Double) objects[1];
            long amount = (Long) objects[2];
            appTopConsumeModel.setPictureUrl(user.getUserFace());
            appTopConsumeModel.setNickName(user.getNickname());
            appTopConsumeModel.setName(user.getUsername());
            appTopConsumeModel.setMoney((float) money);
            appTopConsumeModel.setMobile(user.getMobile());
            appTopConsumeModel.setAmount((int) amount);
            appTopConsumeModels[i] = appTopConsumeModel;
        }
        list.outputData(appTopConsumeModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/topSales")
    public ApiResult topSales(Output<AppTopSalesModel[]> list) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        List<Object[]> toplist = orderItemsService.countTopGoodList(merchant, new PageRequest(0, TOP_PAGE - 10)).getContent();
        AppTopSalesModel[] appTopSalesModels = new AppTopSalesModel[toplist.size()];
        for (int i = 0; i < toplist.size(); i++) {
            AppTopSalesModel appTopSalesModel = new AppTopSalesModel();
            Object[] objects = toplist.get(i);
            Integer goodId = (Integer) objects[0];
            Goods goods = goodsRepository.findOne(goodId);
            long amount = (Long) objects[1];
            appTopSalesModel.setName(goods.getTitle());
            appTopSalesModel.setAmount((int) amount);
            appTopSalesModels[i] = appTopSalesModel;
        }
        list.outputData(appTopSalesModels);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

    @Override
    @RequestMapping("/otherStatistics")
    public ApiResult otherStatistics(Output<AppOtherInfoModel> otherInfoList) throws Exception {
        Merchant merchant = PublicParameterHolder.getParameters().getCurrentUser();
        AppOtherInfoModel appOtherInfoModel = new AppOtherInfoModel();
        appOtherInfoModel.setBillAmount(countService.getTotalOrders(merchant));
        appOtherInfoModel.setGoodsAmount(goodsService.countByMerchant(merchant));
        appOtherInfoModel.setDiscributorAmount(countService.getTotalPartner(merchant));
        appOtherInfoModel.setMemberAmount(countService.getTotalMembers(merchant));
        otherInfoList.outputData(appOtherInfoModel);
        return ApiResult.resultWith(CommonEnum.AppCode.SUCCESS);
    }

}
