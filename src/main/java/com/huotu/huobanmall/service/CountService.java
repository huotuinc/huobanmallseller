package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppBillReportListModel;

import java.util.List;

/**
 * Created by Shiliting on 2015/8/27.
 */
public interface CountService {



    List<AppBillReportListModel> todayOrder(Merchant merchant);

    List<AppBillReportListModel> weekOrder(Merchant merchant);

}
