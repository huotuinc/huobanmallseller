package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Feedback;
import com.huotu.huobanmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CJ
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long>{



}
