package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.model.app.AppTopScoreModel;
import com.huotu.huobanmall.repository.RebateRepository;
import com.huotu.huobanmall.service.CommonConfigService;
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

    @Autowired
    CommonConfigService commonConfigService;

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
            if(!StringUtils.isEmpty(user)){
                AppTopScoreModel appTopScoreModel = new AppTopScoreModel();
                appTopScoreModel.setName(userService.getViewUserName(user));
                appTopScoreModel.setScore(Integer.parseInt(objects[1].toString()));
                appTopScoreModel.setPictureUrl(commonConfigService.getResoureServerUrl()+user.getUserFace());
                result.add(appTopScoreModel);
            }
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

        return list;
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

    @Override
    public String getScoreUserName(Integer gainer) {
        switch (gainer){
            case 1:
                return "购买人返利积分";
            case 2:
                return "上线返利积分";
            case 201:
                return "上上线返利积分";
            case 202:
                return "上上上线返利积分";
            default:
                return "无";
        }
    }


}
