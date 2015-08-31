package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Order;
import com.huotu.huobanmall.repository.OrderRepository;
import com.huotu.huobanmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * 订单Service层
 * Created by shiliting on 2015/8/27.
 */

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;


    /**
     * Created by shiliting on 2015/8/28.
     * 查找订单,按照订单时间降序排序
     * @param lastOrderTime   显示订单的最后一条的下单时间
     * @param pageSize      一次显示订单的数量
     * @param orderStatus   订单的类型
     * @return
     */
    @Override
    public Page<Order> searchOrders(Integer merchantId,Date lastOrderTime, Integer pageSize, Integer orderStatus) {
        return orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(
                        cb.equal(root.get("merchantId").as(Integer.class),merchantId),
                        cb.equal(root.get("orderStatus").as(Integer.class),orderStatus),
                        cb.lessThan(root.get("time").as(Date.class),lastOrderTime)
                );
            }
        },new PageRequest(0,pageSize,new Sort(Sort.Direction.DESC,"time")));
    }
}
