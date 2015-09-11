package com.huotu.huobanmall.model.app;

/**
 * Created by lgh on 2015/9/11.
 */
public class AppTopSalesModel {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品数量
     */
    private Integer amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
