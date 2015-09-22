package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Rebate;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.model.app.AppTopScoreModel;
import com.huotu.huobanmall.repository.RebateRepository;
import com.huotu.huobanmall.service.RebateService;
import com.huotu.huobanmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgh on 2015/9/1.
 */

@Service
public class RebateServiceImpl implements RebateService {
    @Autowired
    RebateRepository rebateRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<AppTopScoreModel> topScore(Merchant merchant, Integer status) {
        StringBuilder hql = new StringBuilder();
        hql.append("select user,sum(r.score) amount from Rebate r left join User user on user.id=r.userId " +
                " where r.merchant.id=:merchantId and r.status=:status" +
                " group by user order by amount desc");
        List list = rebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchant.getId());
            query.setParameter("status", status);
            query.setMaxResults(10);
        });

        List<AppTopScoreModel> result = new ArrayList<>();
        list.forEach(data -> {
            Object[] objects = (Object[]) data;
            User user = (User) objects[0];
            AppTopScoreModel appTopScoreModel = new AppTopScoreModel();
            appTopScoreModel.setName(userService.getViewUserName(user));
            appTopScoreModel.setScore(Integer.parseInt(objects[0].toString()));
            appTopScoreModel.setPictureUrl(user.getUserFace());  //todo 图片路径需要修改
            result.add(appTopScoreModel);
        });
        return result;
    }

    @Override
    public Page<Object[]> searchTopScore(Merchant merchant, Integer status) {
        return rebateRepository.findTopScore(merchant.getId(), status, new PageRequest(0, 20));
    }

    @Override
    public List searchUserScore(Merchant merchant, Integer lastId, String name, Integer pageSize) {
        StringBuffer hql = new StringBuffer();
        hql.append("select rebate,user from Rebate rebate left join User user on rebate.userId=user.id" +
                " where rebate.merchant.id=:merchantId");
        if (lastId != null && lastId > 0) hql.append(" and rebate.id<:Id");
        if (!StringUtils.isEmpty(name))
            hql.append(" and (user.username like :name or user.realName like :name or user.mobile like :name or user.wxNickName like :name)");
        hql.append(" order by rebate.id desc");
        List list = rebateRepository.queryHql(hql.toString(), query -> {
            query.setParameter("merchantId", merchant.getId());
            if (lastId != null && lastId > 0)
                query.setParameter("Id", lastId);
            if (!StringUtils.isEmpty(name))
                query.setParameter("name", "%" + name + "%");
            query.setMaxResults(pageSize);
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
        switch (status) {
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
