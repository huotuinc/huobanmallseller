package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.repository.RebateRepository;
import com.huotu.huobanmall.service.RebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lgh on 2015/9/1.
 */

@Service
public class RebateServiceImpl implements RebateService {
    @Autowired
    RebateRepository rebateRepository;
    @Override
    public Page<Object[]> searchTopScore(Merchant merchant, Integer status) {
        return rebateRepository.findTopScore(merchant,status,new PageRequest(0,20));


    }

    @Override
    public List searchUserScore(Merchant merchant,Integer lastId,String name,Integer pageSize) {
        StringBuffer hql = new StringBuffer();
        hql.append("select top :pageSize rebate,user from Rebate rebate left join User user on rebate.userId=user.id where rebate.merchant.id=:merchantId");
        hql.append(" and rebate.id<:Id and (user.name like :name or user.realName like :name or user.mobile like :name or user.wxNickName like :name)");
        hql.append(" order by rebate.time");
        List list = rebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchant.getId());
            query.setParameter("Id",lastId);
            query.setParameter("name", name);
            query.setParameter("pageSize",pageSize);

        });

//        list.forEach(data->{
//            Object[] objects = (Object[])data;
//            Order order =(Order)objects[0];
//            User user= objects[1]!=null?(User)objects[1]:null;
//            AppTopConsumeModel appTopConsumeModel=new AppTopConsumeModel();
//            appTopConsumeModel.setPictureUrl(user.getUserFace());
//            appTopConsumeModel.setName();
//
//        });

        return list;




//        return rebateRepository.findAll(new Specification<Rebate>() {
//            @Override
//            public Predicate toPredicate(Root<Rebate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                return null;
//            }
//        },new PageRequest(0,20));
//        return rebateRepository.findByMerchantAndStatusLessThanOrderByTimeDesc(merchant, status, time, new PageRequest(0, 20));
    }


    @Override
    public String getScoreStatus(Integer status) {
        switch (status){
            case 0:
                return "待转正";
            case 1:
                return "已转正";
            case -1:
                return "待转正状态下被作废";
            case -2:
                return "已转正状态下被作废";
            default:
                return "无";
        }
    }


}
