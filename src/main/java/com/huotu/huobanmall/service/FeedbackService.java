/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.service;

import com.huotu.huobanmall.api.common.Paging;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.model.app.AppFeedbackModel;

import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public interface FeedbackService {


    /**
     * 插入一条反馈信息
     *
     * @param name
     * @param contact
     * @param content
     * @param merchant
     * @param operator
     * @return
     */
    int insert(String name, String contact, String content, Merchant merchant, Operator operator);
}
