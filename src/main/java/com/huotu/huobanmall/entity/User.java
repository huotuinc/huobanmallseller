package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lgh on 2015/8/26.
 */
@Entity
@Table(name = "USERES")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(nullable = false)
    private String username;

    /**
     * 密码
     */
    private String password;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    private Merchant merchant;

    /**
     * 注册时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date regTime;

    /**
     * 用户类型  0会员 1小伙伴
     */
    private  Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
}
