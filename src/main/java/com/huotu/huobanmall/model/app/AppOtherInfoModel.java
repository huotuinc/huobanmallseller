package com.huotu.huobanmall.model.app;

/**
 * app其他统计数据
 * Created by lgh on 2015/8/24.
 * Modify by shiliting on 2015/9/11
 */
public class AppOtherInfoModel {
    /**
     * 商品总数
     */
    private Integer goodsAmount;

    /**
     * 分销商总数
     */
    private Integer discributorAmount;

    /**
     * 会员总数
     */
    private Integer memberAmount;

    /**
     * 总订单数
     */
    private Integer BillAmount;



    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getDiscributorAmount() {
        return discributorAmount;
    }

    public void setDiscributorAmount(Integer discributorAmount) {
        this.discributorAmount = discributorAmount;
    }

    public Integer getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(Integer memberAmount) {
        this.memberAmount = memberAmount;
    }

    public Integer getBillAmount() {
        return BillAmount;
    }

    public void setBillAmount(Integer billAmount) {
        BillAmount = billAmount;
    }
}
