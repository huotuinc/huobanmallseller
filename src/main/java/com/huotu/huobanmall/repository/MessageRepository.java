/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.Message;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author CJ
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, ClassicsRepository<Message> {

//    List<Message> findByDeletedTrueOrInvalidTimeLessThan(Date date);

//    @Transactional
//    @Modifying
//    @Query("delete from Message  message where message.deleted=true or message.invalidTime<?1")
//    void deleteByDeletedTrueOrInvalidTimeLessThan(Date date);
}
