package com.huotu.huobanmall.repository;

import com.huotu.huobanmall.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2015/9/21.
 */

@Repository
public interface CategoryRepository extends JpaSpecificationExecutor<Category>,JpaRepository<Category,Integer> {
}
