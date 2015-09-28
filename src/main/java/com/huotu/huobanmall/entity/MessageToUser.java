package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户站内消息
 */
@Entity
public class MessageToUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private Message message;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Merchant merchant;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Operator operator;

    private boolean readed;
    private boolean deleted;
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedTime;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }


    public boolean isReaded() {
        return readed;
    }

    public void setReaded(boolean readed) {
        this.readed = readed;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant owner) {
        this.merchant = owner;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}