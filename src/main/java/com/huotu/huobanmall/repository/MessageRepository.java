package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.Message;
import org.luffy.lib.libspring.data.ClassicsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author CJ
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, ClassicsRepository<Message> {

    List<Message> findByDeletedTrueOrInvalidTimeLessThan(Date date);

}
