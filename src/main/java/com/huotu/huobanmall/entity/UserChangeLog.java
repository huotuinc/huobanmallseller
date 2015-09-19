package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户变更表
 * 对应表Mall_Member_ChangeLog 实体MemberChangeLogModel
 * Created by lgh on 2015/9/14.
 */

@Entity
@Table(name = "Mall_Member_ChangeLog")
@Cacheable(value = false)
public class UserChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_Id")
    private Integer id;

    /**
     * 所属商户
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Customer_Id")
    private Merchant merchant;

    /**
     * 变更成员
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "Member_Id")
    private User user;

    /**
     * 变更类型
     会员所属小伙伴变更 = 0,
     小伙伴所属大伙伴变更 = 1,
     会员升级成小伙伴 = 2,
     小伙伴降级为会员 = 3,
     师傅变更 = 4,
     会员注册 = 5,
     小伙伴注册 = 6,
     小伙伴降级 = 7,
     取消获得下线返利资格 = 8,
     获得下线返利资格 = 9,
     会员等级升级 = 10,
     小伙伴等级升级 = 11
     */
    @Column(name = "Change_Type")
    private Integer changeType;


    /**
     * 变更时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Add_Time")
    private Date time;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
