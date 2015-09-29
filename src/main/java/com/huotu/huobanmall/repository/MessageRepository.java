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
