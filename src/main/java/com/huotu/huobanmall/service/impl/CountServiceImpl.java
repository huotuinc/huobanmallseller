package com.huotu.huobanmall.service.impl;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppBillReportListModel;
import com.huotu.huobanmall.service.CountService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户服务类
 * Created by lgh on 2015/8/26.
 */

@Service
public class CountServiceImpl implements CountService {


    @Override
    public List<AppBillReportListModel> todayOrder(Merchant merchant) {
        return null;
    }

    @Override
    public List<AppBillReportListModel> weekOrder(Merchant merchant) {
        return null;
    }
}
