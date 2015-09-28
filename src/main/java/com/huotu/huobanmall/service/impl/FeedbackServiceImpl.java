package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.api.common.Paging;
import com.huotu.huobanmall.entity.Feedback;
import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.entity.Operator;
import com.huotu.huobanmall.entity.User;
import com.huotu.huobanmall.model.app.AppFeedbackModel;
import com.huotu.huobanmall.repository.FeedbackRepository;
import com.huotu.huobanmall.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;



    @Override
    @Transactional
    public int insert(String name, String contact, String content, Merchant merchant, Operator operator) {
        Feedback feedback = new Feedback();
        feedback.setContact(contact);
        feedback.setName(name);
        feedback.setContent(content);
        feedback.setCreateTime(new Date());
        if (merchant != null) feedback.setMerchant(merchant);
        if (operator != null) feedback.setOperator(operator);
        feedbackRepository.save(feedback);
        return 1;
    }
}
