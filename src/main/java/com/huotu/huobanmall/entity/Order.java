package com.huotu.huobanmall.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单
 * Created by lgh on 2015/8/26.
 */
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private User owner;
//
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
//    private Product product;

    /**
     * 用户
     */
    private Integer userId;

    /**
     * 产品Id
     */
    private Integer productId;

    /**
     * 下单时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date time;

    /**
     * 订单状态 1 未付款 2已付款 todo 需要根据实际情况调整
     */
    private Integer orderStatus;
}
