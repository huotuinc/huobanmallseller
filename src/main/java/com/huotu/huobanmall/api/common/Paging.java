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
