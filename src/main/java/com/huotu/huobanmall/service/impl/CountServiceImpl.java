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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Map<Integer,Integer> todayOrder(Merchant merchant) {
        Map<Integer,Integer> map=DateHelper.getTimeAbscissa(Integer.class);
        List<CountTodayOrder> listOrder=countTodayOrderRepository.findByMerchantId(merchant.getId());

        for(int i=1;i<=listOrder.size();i++){
            CountTodayOrder countTodayOrder=listOrder.get(i-1);
            map.put(countTodayOrder.getHour(),countTodayOrder.getAmount());

        }
        return map;
    }

    @Override
    public Map<Integer, Integer> todayMember(Merchant merchant) {
        Map<Integer, Integer> map = DateHelper.getTimeAbscissa(Integer.class);
        List<CountTodayMember> listMember = countTodayMemberRepository.findByMerchantId(merchant.getId());
        for (int i = 1; i <=listMember.size(); i++) {
            CountTodayMember countTodayMember = listMember.get(i-1);
            map.put(countTodayMember.getHour(),countTodayMember.getAmount());
//            int p=(i+2)/3*3;
//            if (map.get(p) == null) {
//                map.put(p, countTodayMember.getAmount());
//            } else {
//                map.put(p, map.get(p) + countTodayMember.getAmount());
//            }
        }
        return map;

    }
    @Override
    public Map<Date, Integer> getWeekOrder(Merchant merchant) {
        Map<Date, Integer> result = DateHelper.getWeekAbscissa(Integer.class);
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
        Map<Date, Integer> result =DateHelper.getWeekAbscissa(Integer.class);
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
        Map<Date, Integer> result = DateHelper.getWeekAbscissa(Integer.class);
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
    public Map<Integer, Double> getDaySales(Merchant merchant) {
        Map<Integer, Double> result = DateHelper.getTimeAbscissa(Double.class);
        Calendar calendar = Calendar.getInstance();
        Integer curHour = calendar.get(Calendar.HOUR_OF_DAY);
        List<CountTodaySales> list = countTodaySalesRepository.findAllByMerchantIdOrderByHour(merchant.getId());

        for (CountTodaySales countTodaySales : list) {
            Integer hour = countTodaySales.getHour();
            if (hour > curHour) break;
            int realhour=(hour+2)/3*3;
            result.put(realhour,result.get(realhour)+countTodaySales.getMoney());
//            count += countTodaySales.getMoney();
//            if (hour % 3 == 0) {
//                result.put(hour, count);
//                count = 0F;
//            }
        }
//        if(list.size()>0){
//            Integer lastHour = list.get(list.size() - 1).getHour();
//            if (lastHour % 3 != 0)
//                result.put(lastHour - lastHour % 3 + 3, count);
//        }
        return result;
    }

    @Override
    public Map<Date, Double> getWeekSales(Merchant merchant) {
        Map<Date, Double> result = DateHelper.getWeekAbscissa(Double.class);
        Date date = DateHelper.getThisWeekBegin();
        List<CountDaySales> list = countDaySalesRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDaySales countDaySales : list) {
            result.put(countDaySales.getDate(), countDaySales.getMoney());
        }
        return result;
    }

    @Override
    public Map<Date, Double> getMonthSales(Merchant merchant) {
        Map<Date, Double> result = new TreeMap<>();
        Date date = DateHelper.getThisMonthBegin();
        List<CountDaySales> list = countDaySalesRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), date);
        for (CountDaySales countDaySales : list) {
            result.put(countDaySales.getDate(), countDaySales.getMoney());
        }
        return result;
    }

    @Override
    public Double getTotalSales(Merchant merchant) {
        List<CountDaySales> countDaySales=countDaySalesRepository.findByMerchantId(merchant.getId());
       return countDaySales.stream().mapToDouble((x)->x.getMoney()).summaryStatistics().getSum();

//        StringBuffer hql = new StringBuffer();
//        hql.append("SELECT SUM(o.price) FROM Order o where o.status<>-1 and o.payStatus=1 and o.merchant.id=:merchantId");
//        List sum =orderRepository.queryHql(hql.toString(), query -> {
//            query.setParameter("merchantId",merchant.getId());
//        });
//        if(StringUtils.isEmpty(sum.get(0))){
//            return 0.0f;
//        }
//        return ((Double)sum.get(0)).floatValue();
////        double data=(Double)sum.get(0);
////        return (float)data;
    }

    @Override
    public Long getTotalOrders(Merchant merchant) {
//        List<CountDayOrder> countDayOrders=countDayOrderRepository.findByMerchantId(merchant.getId());
//        return countDayOrders.stream().mapToLong((x) -> x.getAmount()).summaryStatistics().getSum();
//SELECT ISNULL(SUM(Final_Amount),0) AS TotalAmount,COUNT(1) AS OrderNum FROM Mall_Orders WHERE Status<>-1 AND Pay_Status=1 AND Customer_Id={0}



        StringBuffer hql = new StringBuffer();
        hql.append("select count(1) from Order o where o.status<>-1 and o.payStatus=1 and o.merchant.id=:merchantId");
        List sum =orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId",merchant.getId());
        });


//        return Long.parseLong(sum.get(0).toString());
        return (long)sum.get(0);
    }

    @Override
    public Long getTotalMembers(Merchant merchant) {
//        List<CountDayMember> countDayMembers=countDayMemberRepository.findByMerchantId(merchant.getId());
//        return countDayMembers.stream().mapToLong((x)->x.getAmount()).summaryStatistics().getSum();
        return userRepository.countByMerchantAndTypeNot(merchant,-1);
    }

    @Override
    public Long getTotalMembersType(Merchant merchant,Integer status) {
//        List<CountDayPartner> countDayPartners=countDayPartnerRepository.findByMerchantId(merchant.getId());
//        return countDayPartners.stream().mapToLong((x)->x.getAmount()).summaryStatistics().getSum();
        return userRepository.countByMerchantAndType(merchant,status);
    }

    @Override
    public Map<Integer, Integer> todayPartner(Merchant merchant) {
        Map<Integer,Integer> map=new TreeMap<Integer,Integer>();
        List<CountTodayPartner> listPartner=countTodayPartnerRepository.findByMerchantId(merchant.getId());
        for(int i=1;i<=listPartner.size();i++){
            CountTodayPartner countTodayPartner=listPartner.get(i-1);
            map.put(countTodayPartner.getHour(),countTodayPartner.getAmount());
//            int p=(i+2)/3*3;
//            if(map.get(p)==null){
//                map.put(p,countTodayPartner.getAmount());
//            }else{
//                map.put(p,map.get(p)+countTodayPartner.getAmount());
//            }
        }
        return map;
    }


}
