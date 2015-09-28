package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 站内消息
 */
@Entity
@Table(indexes = {@Index(columnList = "sendTime"), @Index(columnList = "invalidTime"), @Index(columnList = "deleted")})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70)
    private String title;

    @Column(length = 300)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addedTime;

    /**
     * 用户可以看到的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    /**
     * 无法再看到的时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date invalidTime;

    private boolean deleted;

    /**
     * 0:全推送
     * 先忽略，推送重新设计
     */
    private Short messageType;

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getMessageType() {
        return messageType;
    }

    public void setMessageType(Short messageType) {
        this.messageType = messageType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}