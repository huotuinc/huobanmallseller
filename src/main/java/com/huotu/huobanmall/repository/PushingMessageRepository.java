package com.huotu.huobanmall.repository;


import com.huotu.huobanmall.entity.PushingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author CJ
 */
@Repository
public interface PushingMessageRepository extends JpaRepository<PushingMessage, Long> {

}
