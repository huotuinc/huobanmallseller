package com.huotu.huobanmall.service.impl;

import com.huotu.common.DateHelper;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.AppBillReportListModel;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 商户服务类
 * Created by lgh on 2015/8/26.
 */

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private CountDayMemberRepository countDayMemberRepository;

    @Autowired
    private CountDayOrderRepository countDayOrderRepository;

    @Autowired
    private CountDayPartnerRepository countDayPartnerRepository;

    @Autowired
    private CountDaySalesRepository countDaySalesRepository;

    @Autowired
    private CountTodayMemberRepository countTodayMemberRepository;

    @Autowired
    private CountTodayOrderRepository countTodayOrderRepository;

    @Autowired
    private CountTodayPartnerRepository countTodayPartnerRepository;

    @Autowired
    private CountTodaySalesRepository countTodaySalesRepository;


    @Override
    public List<AppBillReportListModel> todayOrder(Merchant merchant) {
        return null;
    }

    @Override
    public List<AppBillReportListModel> weekOrder(Merchant merchant) {
        return null;
    }


    @Override
    public Map<Date, Integer> getWeekOrder(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDayOrder> list = countDayOrderRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDayOrder countDayOrder : list) {
            result.put(countDayOrder.getDate(), countDayOrder.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getMonthOrder(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDayOrder> list = countDayOrderRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDayOrder countDayOrder : list) {
            result.put(countDayOrder.getDate(), countDayOrder.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getWeekMember(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDayMember> list = countDayMemberRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDayMember countDayMember : list) {
            result.put(countDayMember.getDate(), countDayMember.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getMonthMember(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDayMember> list = countDayMemberRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDayMember countDayMember : list) {
            result.put(countDayMember.getDate(), countDayMember.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getWeekPartner(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDayPartner> list = countDayPartnerRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDayPartner countDayPartner : list) {
            result.put(countDayPartner.getDate(), countDayPartner.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getMonthPartner(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDayPartner> list = countDayPartnerRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDayPartner countDayPartner : list) {
            result.put(countDayPartner.getDate(), countDayPartner.getAmount());
        }
        return result;
    }

    @Override
    public Map<Integer, Float> getDaySales(Merchant merchant) {
        Map<Integer, Float> result = new TreeMap<>();
        List<CountTodaySales> list = countTodaySalesRepository.findAllByMerchantIdOrderByHour(merchant.getId());
        Calendar calendar = Calendar.getInstance();
        Integer curHour = calendar.get(Calendar.HOUR_OF_DAY);

        Float count = 0F;
        for (CountTodaySales countTodaySales : list) {
            Integer hour = countTodaySales.getHour();
            if (hour > curHour) break;
            count += countTodaySales.getMoney();
            if (hour % 3 == 0) {
                result.put(hour, count);
                count = 0F;
            }
        }

        Integer lastHour = list.get(list.size() - 1).getHour();
        if (lastHour % 3 != 0)
            result.put(lastHour - lastHour % 3 + 3, count);

        return result;
    }

    @Override
    public Map<Date, Float> getWeekSales(Merchant merchant) {
        Map<Date, Float> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDaySales> list = countDaySalesRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDaySales countDaySales : list) {
            result.put(countDaySales.getDate(), countDaySales.getMoney());
        }
        return result;
    }

    @Override
    public Map<Date, Float> getMonthSales(Merchant merchant) {
        Map<Date, Float> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDaySales> list = countDaySalesRepository.findAllByMerchantIdAndDateGreaterThanEqualsOrderByDate(merchant.getId(), date);
        for (CountDaySales countDaySales : list) {
            result.put(countDaySales.getDate(), countDaySales.getMoney());
        }
        return result;
    }
}
