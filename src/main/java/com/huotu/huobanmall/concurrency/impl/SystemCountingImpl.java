package com.huotu.huobanmall.concurrency.impl;


import com.huotu.common.DateHelper;
import com.huotu.huobanmall.concurrency.SystemCounting;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by lgh on 2015/9/11.
 */

@Service

public class SystemCountingImpl implements SystemCounting {

    private Log log = LogFactory.getLog(SystemCountingImpl.class);

    @Autowired
    private OrderRepository orderRepository;

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


    /**
     * 计算订单量、销售额、会员量、小伙伴
     * 计算频率 每时
     * 凌晨计算完后，需要计算每天量
     */
    @Override
    @Scheduled(cron = "0 41 10 * * ?")
    public void count() {
        Calendar calendar = Calendar.getInstance();
        Integer hour = calendar.get(Calendar.HOUR);

        countHour(hour);

        if (hour == 0) {
            countDay();
        }
    }

    /**
     * 计算指定小时量
     *
     * @param hour
     */
    private void countHour(Integer hour) {
        Date beginTime = DateHelper.getThisDayBegin();
        beginTime.setHours(hour - 1);
        Date endTime = DateHelper.getThisDayBegin();
        endTime.setHours(hour);

        //订单量
        countOrder(beginTime, endTime, hour);
        //销售额
        countSales(beginTime, endTime, hour);
        //会员量
        countMember(beginTime, endTime, hour);
        //小伙伴
        countPartner(beginTime, endTime, hour);

    }

    /**
     * 计算订单量
     *
     * @param beginTime
     * @param endTime
     * @param hour
     */
    private void countOrder(Date beginTime, Date endTime, Integer hour) {
        List<CountTodayOrder> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,count(order) as amount from Order order " +
                " where order.time>=:beginTime and order.time<:endTime" +
                " group by order.merchant.id");
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodayOrder(Integer.parseInt(objects[0].toString())
                    , hour, Integer.parseInt(objects[1].toString())));
        });

        countTodayOrderRepository.save(Arrays.asList(list.toArray(new CountTodayOrder[list.size()])));
    }

    private void countSales(Date beginTime, Date endTime, Integer hour) {
        List<CountTodaySales> list = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,count(order) as amount from Order order " +
                " where order.time>=:beginTime and order.time<:endTime" +
                " group by order.merchant.id");//todo 对应表
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodaySales(Integer.parseInt(objects[0].toString())
                    , hour, Float.parseFloat(objects[1].toString())));
        });

        countTodaySalesRepository.save(Arrays.asList(list.toArray(new CountTodaySales[list.size()])));
    }

    private void countMember(Date beginTime, Date endTime, Integer hour) {
        List<CountTodayMember> list = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,count(order) as amount from Order order " +
                " where order.time>=:beginTime and order.time<:endTime" +
                " group by order.merchant.id");//todo 对应表
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodayMember(Integer.parseInt(objects[0].toString())
                    , hour, Integer.parseInt(objects[1].toString())));
        });

        countTodayMemberRepository.save(Arrays.asList(list.toArray(new CountTodayMember[list.size()])));
    }

    private void countPartner(Date beginTime, Date endTime, Integer hour) {
        List<CountTodayPartner> list = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,count(order) as amount from Order order " +
                " where order.time>=:beginTime and order.time<:endTime" +
                " group by order.merchant.id");//todo 对应表
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodayPartner(Integer.parseInt(objects[0].toString())
                    , hour, Integer.parseInt(objects[1].toString())));
        });

        countTodayPartnerRepository.save(Arrays.asList(list.toArray(new CountTodayPartner[list.size()])));
    }


    /**
     * 计算每天量
     */
    public void countDay() {
        Date date = DateHelper.getThisDayBegin();
        date.setHours(-24);

        countDayMember(date);

        countDayOrder(date);

        countDayPartner(date);

        countDaySales(date);


        //        log.info(list.size());
//        StringBuilder hql = new StringBuilder();
//        hql.append("insert CountDayMember select c.merchantId,:date,sum(c.amount)" +
//                " from CountTodayMember c group by c.merchantId");
//        countDayMemberRepository.executeHql(hql.toString(), query -> {
//            query.setParameter("date", date);
//        });
    }

    private void countDayMember(Date date) {
        StringBuilder hql = new StringBuilder();
        hql.append("select c.merchantId,sum(c.amount) from CountTodayMember c group by c.merchantId");
        List listQuery = countTodayMemberRepository.queryHql(hql.toString(), query -> {
        });

        List<CountDayMember> list = new ArrayList<>();
        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountDayMember(Integer.parseInt(objects[0].toString())
                    , date, Integer.parseInt(objects[1].toString())));
        });
        countDayMemberRepository.save(Arrays.asList(list.toArray(new CountDayMember[list.size()])));
    }

    private void countDayOrder(Date date) {
        StringBuilder hql = new StringBuilder();
        hql.append("select c.merchantId,sum(c.amount) from CountTodayOrder c group by c.merchantId");
        List listQuery = countTodayOrderRepository.queryHql(hql.toString(), query -> {
        });

        List<CountDayOrder> list = new ArrayList<>();
        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountDayOrder(Integer.parseInt(objects[0].toString())
                    , date, Integer.parseInt(objects[1].toString())));
        });
        countDayOrderRepository.save(Arrays.asList(list.toArray(new CountDayOrder[list.size()])));
    }

    private void countDayPartner(Date date) {

        StringBuilder hql = new StringBuilder();
        hql.append("select c.merchantId,sum(c.amount) from CountTodayPartner c group by c.merchantId");
        List listQuery = countTodayPartnerRepository.queryHql(hql.toString(), query -> {
        });

        List<CountDayPartner> list = new ArrayList<>();
        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountDayPartner(Integer.parseInt(objects[0].toString())
                    , date, Integer.parseInt(objects[1].toString())));
        });
        countDayPartnerRepository.save(Arrays.asList(list.toArray(new CountDayPartner[list.size()])));

    }

    private void countDaySales(Date date) {
        StringBuilder hql = new StringBuilder();
        hql.append("select c.merchantId,sum(c.money) from CountTodaySales c group by c.merchantId");
        List listQuery = countTodaySalesRepository.queryHql(hql.toString(), query -> {
        });

        List<CountDaySales> list = new ArrayList<>();
        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountDaySales(Integer.parseInt(objects[0].toString())
                    , date, Float.parseFloat(objects[1].toString())));
        });
        countDaySalesRepository.save(Arrays.asList(list.toArray(new CountDaySales[list.size()])));

    }
}
