package com.huotu.huobanmall.service;

import com.huotu.huobanmall.entity.Merchant;
import com.huotu.huobanmall.model.app.AppTopScoreModel;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by lgh on 2015/9/1.
 */
public interface RebateService {

    /**
     * 返利积分排行 10
     * @param merchant
     * @param status
     * @return
     */
    List<AppTopScoreModel> topScore(Merchant merchant,Integer status);

    /**
     * 最高返利会员
     * @param merchant      所属商家
     * @param status        状态
     * @return
     */
    Page<Object[]> searchTopScore(Merchant merchant, Integer status);

    /**
     * 会员返利列表
     * @param merchant      所属商家
     * @param lastId          最后一条显示的id
     * @param name          搜索关键字
     * @return
     */
    List searchUserScore(Merchant merchant,Integer lastId,String name,Integer pageSize);

    /**
     * 返回积分状态
     * @param status        状态
     * @return
     */
    String getScoreStatus(Integer status);

    /**
     * 获取返利积分人员
     *  购买商品获得 = 1,
     *  下线购买商品提成 = 2,
     *  下下线购买商品提成 = 201,
     *  下下下线购买商品提成 = 202,
     * @param gainer
     * @return
     */
    String getScoreUserName(Integer gainer);





}
