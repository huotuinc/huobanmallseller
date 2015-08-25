package com.huotu.huobanmall.model.app;

/**
 * 购买量排行
 * Created by Administrator on 2015/8/24.
 */
public class AppTopBuyAmountModel {

    /**
     * 商品名称
     */
    private String title;

    /**
     * 购买量
     */
    private Integer amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
