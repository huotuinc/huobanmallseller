package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/21.
 */
public class AppUserRebateModel {
    /**
     * 显示的用户名
     */
    private String userName;

    /**
     * 获得积分时间
     */
    private Date getTime;

    /**
     * 积分转正时间
     */
    private Date Regularization;

    /**
     * 目前的状态
     */
    private String present;

    /**
     * 获得的积分
     */
    private Integer score;

    /**
     * 获得积分用户的类型
     */
    private String userType;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Date getRegularization() {
        return Regularization;
    }

    public void setRegularization(Date regularization) {
        Regularization = regularization;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
