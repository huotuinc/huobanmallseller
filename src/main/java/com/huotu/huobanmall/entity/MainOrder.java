package com.huotu.huobanmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 主订单
 * Created by lgh on 2015/9/25.
 */
@Entity
@Table(name = "Mall_UnionOrder")
public class MainOrder {

    @Id
    @Column(name = "Union_Order_Id")
    private  String id;

    /**
     *时间
     */
    @Column(name = "Createtime")
    private Date  time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
