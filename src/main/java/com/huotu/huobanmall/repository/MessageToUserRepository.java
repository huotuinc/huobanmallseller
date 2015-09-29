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
