package com.huotu.huobanmall.model.app;

import java.util.Date;

/**
 * 消息
 * <p>06-12更新 移除title url增加context</p>
 *
 * @author Administrator
 */
public class AppMessageModel {
    private long messageid;
    private long messageOrder;
    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }

    public long getMessageOrder() {
        return messageOrder;
    }

    public void setMessageOrder(long messageOrder) {
        this.messageOrder = messageOrder;
    }

}
