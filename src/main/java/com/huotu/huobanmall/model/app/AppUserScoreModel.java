package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * Created by lgh on 2015/9/22.
 */
public class AppUserScoreModel {

    /**
     * 用户头像地址
     */
    private String pictureUrl;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 返利积分
     */
    private Integer score;

    /**
     * 时间
     */
    private Date time;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
