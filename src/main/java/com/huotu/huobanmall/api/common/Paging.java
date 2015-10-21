/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.api.common;

/**
 * 分组排序所需的参数
 * @author CJ
 */
public class Paging {
    /**
     * 当前数据最后一个分组依据数值
     *
     */
    private String pagingTag;
    /**
     * 分组长度
     */
    private int pagingSize;

    public int getPagingSize() {
        return pagingSize;
    }

    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }

    public String getPagingTag() {
        return pagingTag;
    }

    public void setPagingTag(String pagingTag) {
        this.pagingTag = pagingTag;
    }
}
