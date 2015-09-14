package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 订单Service层
 * Created by shiliting on 2015/8/27.
 */

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    RebateRepository rebateRepository;
    @Autowired
    CountTodaySalesRepository countTodaySalesRepository;
    @Autowired
    CountDayOrderRepository countDayOrderRepository;
    @Autowired
    CountTodayOrderRepository countTodayOrderRepository;



    @Override
    public Page<Order> searchOrders(Integer merchantId,Date time, Integer pageSize, Integer orderStatus) {
        return orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(time==null&&orderStatus==null){
                    return cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId);
                }else if(time==null){
                    return cb.and(
                            cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                            cb.equal(root.get("orderStatus").as(Integer.class),orderStatus)
                    );
                }else if(orderStatus==null){
                    return cb.and(
                            cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                            cb.lessThan(root.get("time").as(Date.class),time)
                    );
                }else{
                    return cb.and(
                            cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                            cb.equal(root.get("orderStatus").as(Integer.class),orderStatus),
                            cb.lessThan(root.get("time").as(Date.class),time)
                    );
                }

            }
        },new PageRequest(0,pageSize,new Sort(Sort.Direction.DESC,"time")));
    }

    @Override
    public Integer countOrderQuantity(Merchant merchant, Date lastTime) {
        List<CountDayOrder> countDayOrders= countDayOrderRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), lastTime);
        int orders=0;
        for(CountDayOrder o:countDayOrders){
            orders+=o.getAmount();
        }
        return orders;
//        return orderRepository.findByMerchantAndTimeGreaterThan(merchant, lastTime).size();
    }

    @Override
    public Integer countOrderQuantity(Merchant merchant) {
        List<CountTodayOrder> countTodayOrders= countTodayOrderRepository.findByMerchantId(merchant.getId());
        int todayOrders=0;
        for(CountTodayOrder o:countTodayOrders){
            todayOrders+=o.getAmount();
        }
        return todayOrders;
    }

    @Override
    public float countSale(Merchant merchant) {
        List<CountTodaySales> countTodayOrderList=countTodaySalesRepository.findAllByMerchantIdOrderByHour(merchant.getId());
//        List<Order> list=orderRepository.findByMerchantAndTimeGreaterThan(merchant, lastTime);
        float sum=0;
        for(CountTodaySales sales:countTodayOrderList){
            sum+=sales.getMoney();
        }
        return sum;
    }

//    @Override
//    public float countSale(Merchant merchant) {
//        List<Order> list=orderRepository.findByMerchant(merchant);
//        float sum=0;
//        for(Order o:list){
//            if(o.getOrderStatus()==3){
//                sum=sum+o.getPrice();
//            }
//
//        }
//        return sum;
//    }

    @Override
    public Page<Rebate> countUserScoreList(Merchant merchant,Pageable pageable) {
        return rebateRepository.findByMerchantAndStatusOrderByScoreDesc(merchant,1,pageable);
//          return null;
    }

    @Override
    public Page<Object[]> countUserExpenditureList(Merchant merchant,Pageable pageable) {
        return orderRepository.countUserExpenditure(merchant,pageable);
    }
}
