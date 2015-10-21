/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

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
     * 物流(快递)图片
     */
    private String pictureURL;

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
    private List<AppLogisticsDataModel> track;

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

    public List<AppLogisticsDataModel> getTrack() {
        return track;
    }

    public void setTrack(List<AppLogisticsDataModel> track) {
        this.track = track;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}