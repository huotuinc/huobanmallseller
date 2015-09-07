package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
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
import java.util.List;

/**
 * 订单Service层
 * Created by shiliting on 2015/8/27.
 */

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;



    @Override
    public Page<Order> searchOrders(Integer merchantId,String lastId, Integer pageSize, Integer orderStatus) {
        return orderRepository.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(lastId==null&&orderStatus==null){
                    return cb.equal(root.get("merchant").get("id").as(Integer.class), merchantId);
                }else if(lastId==null){
                    return cb.and(
                            cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                            cb.equal(root.get("orderStatus").as(Integer.class),orderStatus)
                    );
                }else if(orderStatus==null){
                    return cb.and(
                            cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                            cb.lessThan(root.get("id").as(String.class),lastId)
                    );
                }else{
                    return cb.and(
                            cb.equal(root.get("merchant").get("id").as(Integer.class),merchantId),
                            cb.equal(root.get("orderStatus").as(Integer.class),orderStatus),
                            cb.lessThan(root.get("id").as(String.class),lastId)
                    );
                }

            }
        },new PageRequest(0,pageSize,new Sort(Sort.Direction.DESC,"id")));
    }

    @Override
    public Integer countOrderQuantity(Merchant merchant, Date lastTime) {
        return orderRepository.findByMerchantAndTimeGreaterThan(merchant, lastTime).size();
    }

    @Override
    public Integer countOrderQuantity(Merchant merchant) {
        return orderRepository.findByMerchant(merchant).size();
    }

    @Override
    public float countSale(Merchant merchant, Date lastTime) {
        List<Order> list=orderRepository.findByMerchantAndTimeGreaterThan(merchant, lastTime);
        float sum=0;
        for(Order o:list){
            if(o.getOrderStatus()==3) {
                sum = sum + o.getAmount() * o.getPrice();
            }
        }
        return sum;
    }

    @Override
    public float countSale(Merchant merchant) {
        List<Order> list=orderRepository.findByMerchant(merchant);
        float sum=0;
        for(Order o:list){
            if(o.getOrderStatus()==3){
                sum=sum+o.getAmount()*o.getPrice();
            }

        }
        return sum;
    }

    @Override
    public List<Object[]> countUserScoreList(Merchant merchant) {
        return orderRepository.countUserScore();
    }

    @Override
    public List<Object[]> countUserExpenditureList(Merchant merchant) {
        return orderRepository.countUserExpenditure();
    }
}
