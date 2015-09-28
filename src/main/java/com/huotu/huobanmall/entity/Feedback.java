package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户反馈实体类
 * Created by Administrator on 2015/6/8.
 */
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private Merchant merchant;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
    private Operator operator;

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 50)
    private String contact;
    @Column(nullable = false, length = 300)
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTime;
    private boolean done;
    @Column(length = 100)
    private String remark;
    @Temporal(TemporalType.TIMESTAMP)
    private Date doneTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDoneTime() {
        return doneTime;
    }



    public void setDoneTime(Date doTime) {
        this.doneTime = doTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
