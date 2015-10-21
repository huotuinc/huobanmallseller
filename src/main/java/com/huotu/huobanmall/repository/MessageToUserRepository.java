/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.MessageToUser;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by lgh on 2015/9/25.
 */
@Repository
public interface MessageToUserRepository extends JpaRepository<MessageToUser, Long>, ClassicsRepository<MessageToUser>, JpaSpecificationExecutor<MessageToUser> {

    /**
     * 返回所有已过期的消息
     *
     * @param date
     * @return
     */
//    List<MessageToUser> findByMessage_DeletedTrueOrMessage_InvalidTimeLessThan(Date date);
//    @Transactional
//    @Modifying
//    @Query("delete from MessageToUser toUser where  toUser.message.deleted=true or toUser.message.invalidTime<?1")
//    void deleteByMessage_DeletedTrueOrMessage_InvalidTimeLessThan(Date date);
}
