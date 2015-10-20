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
public class OrderServiceImpl implements OrderService {

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
    @Autowired
    MainOrderRepository mainOrderRepository;


    @Override
    public Page<Order> searchOrdersDetail(Integer merchantId, Date time, Integer pageSize, String keyword) {//todo 检索规则需要修改，状态有多个
        return orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(StringUtils.isEmpty(keyword)&&StringUtils.isEmpty(time)){
                    return cb.and(
                            cb.equal(root.get("payStatus").as(Integer.class),1),
                            cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId)
                    );

                }else if(StringUtils.isEmpty(keyword)){
                    return cb.and(
                            cb.equal(root.get("payStatus").as(Integer.class),1),
                            cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId),
                            cb.lessThan(root.get("time").as(Date.class), time)
                    );
                }else if(StringUtils.isEmpty(time)){
                    return cb.and(
                            cb.equal(root.get("payStatus").as(Integer.class),1),
                            cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId),
                            cb.like(root.get("id").as(String.class), "%" + keyword + "%")
                    );
                }else {
                    return cb.and(
                            cb.equal(root.get("payStatus").as(Integer.class),1),
                            cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId),
                            cb.lessThan(root.get("time").as(Date.class), time),
                            cb.like(root.get("id").as(String.class), "%" + keyword + "%")
                    );
                }

            }
        }, new PageRequest(0, pageSize, new Sort(Sort.Direction.DESC, "time")));
    }

    @Override
    public List<Order> searchOrders(Integer merchantId, Date time, Integer pageSize, Integer orderStatus, String keyword) {
        StringBuffer hql = new StringBuffer();
        hql.append("select order from Order order where order.merchant.id=:merchantId");
        switch (orderStatus){
            case 1:
                hql.append(" and (order.payStatus=0 or order.payStatus=3)");
                break;
            case 2:
                hql.append(" and (order.deliverStatus=0 or order.deliverStatus=1)");
                break;
            case 3:
                hql.append(" and order.status=1");
                break;
            default:
                break;
        }
        if (!StringUtils.isEmpty(keyword)) {
            hql.append(" and order.id like :orderId");
        }
        if (!StringUtils.isEmpty(time)) {
            hql.append(" and order.time<:time");
        }
        hql.append(" order by order.time desc");

        List<Order> list = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId",merchantId);
            if (!StringUtils.isEmpty(time)) {
                query.setParameter("time", time);
            }
            if (!StringUtils.isEmpty(keyword)) {
                query.setParameter("orderId", "%" + keyword + "%");
            }
            query.setMaxResults(pageSize);

        });

        return list;

    }

    @Override
    public List<MainOrder> searchMainOrders(Integer merchantId, Date time, Integer pageSize, Integer orderStatus, String keyword) {
        StringBuffer hql = new StringBuffer();
        hql.append("select o from MainOrder o where o.merchant.id=:merchantId");
        switch (orderStatus){
            case 1:
                hql.append(" and o.payStatus=0");
                break;
            case 2:
                hql.append(" and o.payStatus=1");
                break;
            case 3:
                hql.append(" and o.receivestatus=1");
                break;
            default:
                break;
        }
        if (!StringUtils.isEmpty(keyword)) {
            hql.append(" and o.id like :mainOrderId");
        }
        if (!StringUtils.isEmpty(time)) {
            hql.append(" and o.time<:time");
        }
        hql.append(" order by o.time desc");
        List<MainOrder> list =(List<MainOrder>)mainOrderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId",merchantId);
            if (!StringUtils.isEmpty(time)) {
                query.setParameter("time", time);
            }
            if (!StringUtils.isEmpty(keyword)) {
                query.setParameter("mainOrderId", "%" + keyword + "%");
            }
            query.setMaxResults(pageSize);
        });
        return list;
    }

    @Override
    public Integer countOrderQuantity(Merchant merchant, Date lastTime) {
        List<CountDayOrder> countDayOrders = countDayOrderRepository.findByMerchantIdAndDateGreaterThanEqualOrderByDate(merchant.getId(), lastTime);
        int orders = 0;
        for (CountDayOrder o : countDayOrders) {
            orders += o.getAmount();
        }
        return orders;
//        return orderRepository.findByMerchantAndTimeGreaterThan(merchant, lastTime).size();
    }

    @Override
    public Integer countOrderQuantity(Merchant merchant) {
        List<CountTodayOrder> countTodayOrders = countTodayOrderRepository.findByMerchantId(merchant.getId());
        int todayOrders = 0;
        for (CountTodayOrder o : countTodayOrders) {
            todayOrders += o.getAmount();
        }
        return todayOrders;
    }

    @Override
    public float countSale(Merchant merchant) {
        List<CountTodaySales> countTodayOrderList = countTodaySalesRepository.findAllByMerchantIdOrderByHour(merchant.getId());
        float sum = 0;
        for (CountTodaySales sales : countTodayOrderList) {
            sum += sales.getMoney();
        }
        return sum;
    }


    @Override
    public Page<Object[]> searchTopExpenditure(Merchant merchant, Pageable pageable) {
        return orderRepository.countUserExpenditure(merchant, pageable);
    }

    @Override
    public List searchExpenditureList(Merchant merchant, String name, Date time, Integer pageSize) {
        StringBuffer hql = new StringBuffer();
        hql.append("select order,user from Order order left join User user on order.userId=user.id where order.merchant.id=:merchantId and order.payStatus=1");
        if (!StringUtils.isEmpty(time)) {
            hql.append(" and order.time<:time");
        }
        if (!StringUtils.isEmpty(name)) {
            hql.append(" and (user.username like :name or user.realName like :name or user.mobile like :name or user.wxNickName like :name)");
        }
        hql.append(" order by order.time desc");
        List list = orderRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchant.getId());
            if (!StringUtils.isEmpty(time)) {
                query.setParameter("time", time);
            }
            if (!StringUtils.isEmpty(name)) {
                query.setParameter("name", "%" + name + "%");
            }

            query.setMaxResults(pageSize);

        });
        return list;
    }

    @Override
    public Page<Order> searchTopOrder(Merchant merchant, Integer payStatus, Pageable pageable) {
        return orderRepository.findByMerchantAndPayStatusOrderByPriceDesc(merchant, payStatus, pageable);
    }


    @Override
    public String getPayStatus(Integer status) {
        status=StringUtils.isEmpty(status)? -1:status;
        switch (status){
            case 0:
                return "未支付";
            case 1:
                return "已支付";
            case 2:
                return "已支付至担保方";
            case 3:
                return "部分付款";
            case 4:
                return "部分退款";
            case 5:
                return "全额退款";
            default:
                return "无";
        }
    }

    @Override
    public String getDeliverStatus(Integer status) {
        status=StringUtils.isEmpty(status)? -1:status;
        switch (status){
            case 0:
                return "未发货";
            case 1:
                return "已发货";
            case 2:
                return "部分发货";
            case 3:
                return "部分退货";
            case 4:
                return "已退货";
            default:
                return "无";
        }
    }

    @Override
    public String getOrderStatus(Integer status) {
        status=StringUtils.isEmpty(status)? -1:status;
        switch (status){
            case 0:
                return "活动";
            case -1:
                return "已关闭";
            case 2:
                return "已完成";
            default:
                return "无";
        }
    }
}
