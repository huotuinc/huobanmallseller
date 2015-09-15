package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 物流详情数据
 * Created by lgh on 2015/9/15.
 */
public class AppLogisticsDetailModel {

    /**
     * 物流状态
     */
    private String status;

    /**
     * 信息来源
     */
    private String source;

    /**
     * 运单编号
     */
    private String no;

    /**
     * 规格数据
     */
    private List<AppOrderListProductModel> list;

    /**
     * 物流跟踪
     */
    private String track;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<AppOrderListProductModel> getList() {
        return list;
    }

    public void setList(List<AppOrderListProductModel> list) {
        this.list = list;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}