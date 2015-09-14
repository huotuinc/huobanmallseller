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
    private Long goodsAmount;

    /**
     * 分销商总数
     */
    private Long discributorAmount;

    /**
     * 会员总数
     */
    private Long memberAmount;

    /**
     * 总订单数
     */
    private Long BillAmount;


    public Long getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Long goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Long getDiscributorAmount() {
        return discributorAmount;
    }

    public void setDiscributorAmount(Long discributorAmount) {
        this.discributorAmount = discributorAmount;
    }

    public Long getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(Long memberAmount) {
        this.memberAmount = memberAmount;
    }

    public Long getBillAmount() {
        return BillAmount;
    }

    public void setBillAmount(Long billAmount) {
        BillAmount = billAmount;
    }
}
