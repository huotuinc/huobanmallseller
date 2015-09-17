package com.huotu.huobanmall.service.impl;

import com.huotu.common.DateHelper;
import com.huotu.huobanmall.entity.*;
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
    public Map<Integer,Integer> todayOrder(Merchant merchant) {
        Map<Integer,Integer> map=new TreeMap<Integer,Integer>();
        List<CountTodayOrder> listOrder=countTodayOrderRepository.findByMerchantId(merchant.getId());
        for(int i=1;i<=listOrder.size();i++){
            CountTodayOrder countTodayOrder=listOrder.get(i-1);
            int p=(i+2)/3*3;
            if(map.get(p)==null){
                map.put(p,countTodayOrder.getAmount());
            }else{
                map.put(p,map.get(p)+countTodayOrder.getAmount());
            }

        }
        return map;
    }

    @Override
    public Map<Integer, Integer> todayMember(Merchant merchant) {
        Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
        List<CountTodayMember> listMember = countTodayMemberRepository.findByMerchantId(merchant.getId());
        for (int i = 1; i <=listMember.size(); i++) {
            CountTodayMember countTodayMember = listMember.get(i-1);
            int p=(i+2)/3*3;
            if (map.get(p) == null) {
                map.put(p, countTodayMember.getAmount());
            } else {
                map.put(p, map.get(p) + countTodayMember.getAmount());
            }
        }
        return map;

    }
    @Override
    public Map<Date, Integer> getWeekOrder(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDayOrder> list = countDayOrderRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDayOrder countDayOrder : list) {
            result.put(countDayOrder.getDate(), countDayOrder.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getMonthOrder(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDayOrder> list = countDayOrderRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDayOrder countDayOrder : list) {
            result.put(countDayOrder.getDate(), countDayOrder.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getWeekMember(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDayMember> list = countDayMemberRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDayMember countDayMember : list) {
            result.put(countDayMember.getDate(), countDayMember.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getMonthMember(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDayMember> list = countDayMemberRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDayMember countDayMember : list) {
            result.put(countDayMember.getDate(), countDayMember.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getWeekPartner(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisWeekBegin();
        List<CountDayPartner> list = countDayPartnerRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDayPartner countDayPartner : list) {
            result.put(countDayPartner.getDate(), countDayPartner.getAmount());
        }
        return result;
    }

    @Override
    public Map<Date, Integer> getMonthPartner(Merchant merchant) {
        Map<Date, Integer> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDayPartner> list = countDayPartnerRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
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
        List<CountDaySales> list = countDaySalesRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDaySales countDaySales : list) {
            result.put(countDaySales.getDate(), countDaySales.getMoney());
        }
        return result;
    }

    @Override
    public Map<Date, Float> getMonthSales(Merchant merchant) {
        Map<Date, Float> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDaySales> list = countDaySalesRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDaySales countDaySales : list) {
            result.put(countDaySales.getDate(), countDaySales.getMoney());
        }
        return result;
    }

    @Override
    public Float getTotalSales(Merchant merchant) {
        List<CountDaySales> countDaySales=countDaySalesRepository.findByMerchantId(merchant.getId());
       return ((Number)  countDaySales.stream().mapToDouble((x)->x.getMoney()).summaryStatistics().getSum()).floatValue();
    }

    @Override
    public Long getTotalOrders(Merchant merchant) {
        List<CountDayOrder> countDayOrders=countDayOrderRepository.findByMerchantId(merchant.getId());
        return countDayOrders.stream().mapToLong((x) -> x.getAmount()).summaryStatistics().getSum();
    }

    @Override
    public Long getTotalMembers(Merchant merchant) {
        List<CountDayMember> countDayMembers=countDayMemberRepository.findByMerchantId(merchant.getId());
        return countDayMembers.stream().mapToLong((x)->x.getAmount()).summaryStatistics().getSum();
    }

    @Override
    public Long getTotalPartner(Merchant merchant) {
        List<CountDayPartner> countDayPartners=countDayPartnerRepository.findByMerchantId(merchant.getId());
        return countDayPartners.stream().mapToLong((x)->x.getAmount()).summaryStatistics().getSum();
    }

    @Override
    public Map<Integer, Integer> todayPartner(Merchant merchant) {
        Map<Integer,Integer> map=new TreeMap<Integer,Integer>();
        List<CountTodayPartner> listPartner=countTodayPartnerRepository.findByMerchantId(merchant.getId());
        for(int i=1;i<=listPartner.size();i++){
            CountTodayPartner countTodayPartner=listPartner.get(i-1);
            int p=(i+2)/3*3;
            if(map.get(p)==null){
                map.put(p,countTodayPartner.getAmount());
            }else{
                map.put(p,map.get(p)+countTodayPartner.getAmount());
            }
        }
        return map;
    }


}
