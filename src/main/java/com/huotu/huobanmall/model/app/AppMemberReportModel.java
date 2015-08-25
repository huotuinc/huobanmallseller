package com.huotu.huobanmall.model.app;

import java.util.List;

/**
 * 会员量统计报表
 * Created by Administrator on 2015/8/24.
 */
public class AppMemberReportModel {

    /**
     * 本周小伙伴
     */
    private Integer weekPartner;

    /**
     * 本周会员
     */
    private Integer weekMember;

    /**
     * 本月小伙伴
     */
    private Integer monthPartner;

    /**
     * 本月会员
     */
    private Integer monthMember;
    /**
     * 小伙伴数据
     */
    private List<AppMemberReportListModel> listPartner;
    /**
     * 会员数据
     */
    private List<AppMemberReportListModel> listMember;

    public Integer getWeekPartner() {
        return weekPartner;
    }

    public void setWeekPartner(Integer weekPartner) {
        this.weekPartner = weekPartner;
    }

    public Integer getWeekMember() {
        return weekMember;
    }

    public void setWeekMember(Integer weekMember) {
        this.weekMember = weekMember;
    }

    public Integer getMonthPartner() {
        return monthPartner;
    }

    public void setMonthPartner(Integer monthPartner) {
        this.monthPartner = monthPartner;
    }

    public Integer getMonthMember() {
        return monthMember;
    }

    public void setMonthMember(Integer monthMember) {
        this.monthMember = monthMember;
    }

    public List<AppMemberReportListModel> getListPartner() {
        return listPartner;
    }

    public void setListPartner(List<AppMemberReportListModel> listPartner) {
        this.listPartner = listPartner;
    }

    public List<AppMemberReportListModel> getListMember() {
        return listMember;
    }

    public void setListMember(List<AppMemberReportListModel> listMember) {
        this.listMember = listMember;
    }
}
