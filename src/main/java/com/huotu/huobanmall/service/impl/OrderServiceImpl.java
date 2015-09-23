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
import org.springframework.util.StringUtils;

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
    public Page<Order> searchOrders(Integer merchantId,Date time, Integer pageSize, Integer orderStatus,String keyword) {//todo 检索规则需要修改，状态有多个
        return orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                switch (orderStatus){
                    case 1:
                        return cb.and(
                                cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                                cb.equal(root.get("payStatus").as(Integer.class),0),
                                cb.lessThan(root.get("time").as(Date.class),time),
                                cb.like(root.get("id").as(String.class),"%"+keyword+"%")
                        );
                    case 2:
                        return cb.and(
                                cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                                cb.equal(root.get("payStatus").as(Integer.class),1),
                                cb.lessThan(root.get("time").as(Date.class),time),
                                cb.like(root.get("id").as(String.class),"%"+keyword+"%")
                        );
                    case 3:
                        return cb.and(
                                cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                                cb.equal(root.get("status").as(Integer.class),1),
                                cb.lessThan(root.get("time").as(Date.class),time),
                                cb.like(root.get("id").as(String.class),"%"+keyword+"%")
                        );
                    default:
                        return cb.and(
                                cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                                cb.lessThan(root.get("time").as(Date.class),time),
                                cb.like(root.get("id").as(String.class),"%"+keyword+"%")
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
        float sum=0;
        for(CountTodaySales sales:countTodayOrderList){
            sum+=sales.getMoney();
        }
        return sum;
    }


    @Override
    public Page<Object[]> searchTopExpenditure(Merchant merchant,Pageable pageable) {
        return orderRepository.countUserExpenditure(merchant,pageable);
    }

    @Override
    public List searchExpenditureList(Merchant merchant,String name, Date time, Integer pageSize) {
        StringBuffer hql = new StringBuffer();
        hql.append("select order,user from Order order left join User user on order.userId=user.id where order.merchant.id=:merchantId");
        if(!StringUtils.isEmpty(time)){
            hql.append(" and order.time<:time");
        }
        if(!StringUtils.isEmpty(name)){
            hql.append(" and (user.username like :name or user.realName like :name or user.mobile like :name or user.wxNickName like :name)");
        }
        hql.append(" order by order.time desc");
        List list = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchant.getId());
           if(!StringUtils.isEmpty(time)){
                query.setParameter("time",time);
           }
            if(!StringUtils.isEmpty(name)){
                query.setParameter("name", "%"+name+"%");
            }

            query.setMaxResults(pageSize);

        });
        return list;
    }

    @Override
    public Page<Order> searchTopOrder(Merchant merchant, Integer payStatus, Pageable pageable) {
        return orderRepository.findByMerchantAndPayStatusOrderByPriceDesc(merchant,payStatus,pageable);
    }
}
