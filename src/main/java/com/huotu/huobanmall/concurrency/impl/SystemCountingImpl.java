/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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

import javax.transaction.Transactional;
import java.text.ParseException;
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


    @Autowired
    private UserChangeLogRepository userChangeLogRepository;

    /**
     * 计算订单量、销售额、会员量、小伙伴
     * 计算频率 每时
     * 凌晨计算完后，需要计算每天量
     */
    @Override
    @Scheduled(cron = "0 10 0/1 * * ?")
    @Transactional
    public void count() {
        try {
            log.info("小时计算服务已开启");

            Calendar calendar = Calendar.getInstance();
            Integer hour = calendar.get(Calendar.HOUR_OF_DAY);

            countHour(hour);

            if (hour == 0) {
                log.info("每天计算服务已开启");

                //计算昨天数据
                countDay();

                //清除昨日小时数据
                deleteYesterdayHour();


            }
        } catch (Exception ex) {
            log.error("计算服务异常");
            log.error(ex);
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
                " where order.status<>-1 and order.payStatus=1 and order.time>=:beginTime and order.time<:endTime" +
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
        hql.append("select order.merchant.id,sum(order.price) as amount from Order order " +
                " where order.payTime>=:beginTime and order.payTime<:endTime and order.payStatus=1 and order.status<>-1 " +
                " group by order.merchant.id");
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
        hql.append("select log.merchant.id,count(log) as amount from UserChangeLog log " +
                " where log.time>=:beginTime and log.time<:endTime and log.changeType=:changeType" +
                " group by log.merchant.id");
        List listQuery = userChangeLogRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
            query.setParameter("changeType", 5);
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
        hql.append("select log.merchant.id,count(log) as amount from UserChangeLog log " +
                " where log.time>=:beginTime and log.time<:endTime and log.changeType in (2,6)" +
                " group by log.merchant.id");
        List listQuery = userChangeLogRepository.queryHql(hql.toString(), query -> {
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


    /**
     * 清除昨日数据
     */
    private void deleteYesterdayHour() {
        countTodayMemberRepository.deleteAll();
        countTodayOrderRepository.deleteAll();
        countTodayPartnerRepository.deleteAll();
        countTodaySalesRepository.deleteAll();
    }


    /**
     * 初始化历史数据 todo 上线前需要初始化
     * 包含当前的销售数据
     *
     * @throws ParseException
     */
    @Override
    public void InitHistoryDayAndToday() throws ParseException {
        Date startTime = DateHelper.getThisDayBegin();

        log.info("初始化" + startTime + "之前的天数据");

        InitDayOrder(startTime);

        InitDaySales(startTime);

        InitDayMember(startTime);

        InitDayPartner(startTime);


        Date beginHour = DateHelper.getThisDayBegin();

        Date endHour = DateHelper.getThisDayBegin();
        Calendar calendar = Calendar.getInstance();
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        endHour.setHours(hour);

        log.info("初始化" + beginHour + "到" + endHour + "之前的小时数据");
        initOrder(beginHour, endHour);

        initSales(beginHour, endHour);

        initMember(beginHour, endHour);

        initPartner(beginHour, endHour);
    }


    /**
     * 当天之前的订单
     *
     * @param startTime
     * @throws ParseException
     */
    private void InitDayOrder(Date startTime) throws ParseException {
        List<CountDayOrder> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,FUNC('year',order.time) as year,FUNC('month',order.time) as month,FUNC('day',order.time) as day" +
                " ,count(order) as amount from Order order " +
                " where order.status<>-1 and order.payStatus=1 and order.time<:startTime " +
                " group by order.merchant.id,year,month,day");
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("startTime", startTime);
        });

        for (Object data : listQuery) {

            Object[] objects = (Object[]) data;
            list.add(new CountDayOrder(Integer.parseInt(objects[0].toString())
                    , DateHelper.getThisDayBegin(Integer.parseInt(objects[1].toString()), Integer.parseInt(objects[2].toString()), Integer.parseInt(objects[3].toString()))
                    , Integer.parseInt(objects[4].toString())));
        }
        countDayOrderRepository.save(Arrays.asList(list.toArray(new CountDayOrder[list.size()])));
    }

    private void InitDaySales(Date startTime) throws ParseException {
        List<CountDaySales> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,FUNC('year',order.payTime) as year,FUNC('month',order.payTime) as month,FUNC('day',order.payTime) as day" +
                " ,sum(order.price) as amount from Order order " +
                " where order.payTime<:startTime and order.payStatus=1 and order.status<>-1 " +
                " group by order.merchant.id,year,month,day");
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("startTime", startTime);
        });

        for (Object data : listQuery) {

            Object[] objects = (Object[]) data;
            list.add(new CountDaySales(Integer.parseInt(objects[0].toString())
                    , DateHelper.getThisDayBegin(Integer.parseInt(objects[1].toString()), Integer.parseInt(objects[2].toString()), Integer.parseInt(objects[3].toString()))
                    , Double.parseDouble(objects[4].toString())));
        }
        countDaySalesRepository.save(Arrays.asList(list.toArray(new CountDaySales[list.size()])));
    }


    private void InitDayMember(Date startTime) throws ParseException {
        List<CountDayMember> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select log.merchant.id,FUNC('year',log.time) as year,FUNC('month',log.time) as month,FUNC('day',log.time) as day" +
                " ,count(log) as amount from UserChangeLog log " +
                " where log.time<:startTime and log.changeType=:changeType " +
                " group by log.merchant.id,year,month,day");
        List listQuery = userChangeLogRepository.queryHql(hql.toString(), query -> {
            query.setParameter("startTime", startTime);
            query.setParameter("changeType", 5);
        });

        for (Object data : listQuery) {

            Object[] objects = (Object[]) data;
            list.add(new CountDayMember(Integer.parseInt(objects[0].toString())
                    , DateHelper.getThisDayBegin(Integer.parseInt(objects[1].toString()), Integer.parseInt(objects[2].toString()), Integer.parseInt(objects[3].toString()))
                    , Integer.parseInt(objects[4].toString())));
        }
        countDayMemberRepository.save(Arrays.asList(list.toArray(new CountDayMember[list.size()])));
    }

    private void InitDayPartner(Date startTime) throws ParseException {
        List<CountDayPartner> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select log.merchant.id,FUNC('year',log.time) as year,FUNC('month',log.time) as month,FUNC('day',log.time) as day" +
                " ,count(log) as amount from UserChangeLog log " +
                " where log.time<:startTime and log.changeType in (2,6)" +
                " group by log.merchant.id,year,month,day");
        List listQuery = userChangeLogRepository.queryHql(hql.toString(), query -> {
            query.setParameter("startTime", startTime);
        });

        for (Object data : listQuery) {

            Object[] objects = (Object[]) data;
            list.add(new CountDayPartner(Integer.parseInt(objects[0].toString())
                    , DateHelper.getThisDayBegin(Integer.parseInt(objects[1].toString()), Integer.parseInt(objects[2].toString()), Integer.parseInt(objects[3].toString()))
                    , Integer.parseInt(objects[4].toString())));
        }
        countDayPartnerRepository.save(Arrays.asList(list.toArray(new CountDayPartner[list.size()])));
    }


    private void initOrder(Date beginTime, Date endTime) {
        List<CountTodayOrder> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,FUNC('dbo.hour',order.time) d,count(order) as amount from Order order " +
                " where order.status<>-1 and order.payStatus=1 and order.time>=:beginTime and order.time<:endTime" +
                " group by order.merchant.id,d");
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodayOrder(Integer.parseInt(objects[0].toString())
                    , Integer.parseInt(objects[1].toString())
                    , Integer.parseInt(objects[2].toString())));
        });
        countTodayOrderRepository.save(Arrays.asList(list.toArray(new CountTodayOrder[list.size()])));
    }


    private void initSales(Date beginTime, Date endTime) {
        List<CountTodaySales> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select order.merchant.id,FUNC('dbo.hour',order.payTime) d,sum(order.price) as amount from Order order " +
                " where order.payTime>=:beginTime and order.payTime<:endTime  and order.payStatus=1 and order.status<>-1 " +
                " group by order.merchant.id,d");
        List listQuery = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodaySales(Integer.parseInt(objects[0].toString())
                    , Integer.parseInt(objects[1].toString())
                    , Float.parseFloat(objects[2].toString())));
        });
        countTodaySalesRepository.save(Arrays.asList(list.toArray(new CountTodaySales[list.size()])));
    }


    private void initMember(Date beginTime, Date endTime) {
        List<CountTodayMember> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select log.merchant.id,FUNC('dbo.hour',log.time) d,count(log) as amount from UserChangeLog log " +
                " where log.time>=:beginTime and log.time<:endTime  and log.changeType=:changeType " +
                " group by log.merchant.id,d");
        List listQuery = userChangeLogRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
            query.setParameter("changeType", 5);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodayMember(Integer.parseInt(objects[0].toString())
                    , Integer.parseInt(objects[1].toString())
                    , Integer.parseInt(objects[2].toString())));
        });
        countTodayMemberRepository.save(Arrays.asList(list.toArray(new CountTodayMember[list.size()])));
    }


    private void initPartner(Date beginTime, Date endTime) {
        List<CountTodayPartner> list = new ArrayList<>();

        StringBuilder hql = new StringBuilder();
        hql.append("select log.merchant.id,FUNC('dbo.hour',log.time) d,count(log) as amount from UserChangeLog log " +
                " where log.time>=:beginTime and log.time<:endTime   and log.changeType in (2,6) " +
                " group by log.merchant.id,d");
        List listQuery = userChangeLogRepository.queryHql(hql.toString(), query -> {
            query.setParameter("beginTime", beginTime);
            query.setParameter("endTime", endTime);
        });

        listQuery.forEach(data -> {
            Object[] objects = (Object[]) data;
            list.add(new CountTodayPartner(Integer.parseInt(objects[0].toString())
                    , Integer.parseInt(objects[1].toString())
                    , Integer.parseInt(objects[2].toString())));
        });
        countTodayPartnerRepository.save(Arrays.asList(list.toArray(new CountTodayPartner[list.size()])));
    }

}
