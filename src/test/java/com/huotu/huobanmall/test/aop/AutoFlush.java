package com.huotu.huobanmall.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;

/**
 * Created by lgh on 2015/9/14.
 */
@Aspect
public class AutoFlush {

    @PostConstruct
    public  void init()
    {
        System.out.print("今日");
    }

    @Pointcut(
            "execution(* save(..))" +
                    "&&target(org.springframework.data.jpa.repository.JpaRepository)")
    public void savePoint() {
    }

    @Around("savePoint()")
    public Object aroundSave(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        JpaRepository repository = (JpaRepository) pjp.getTarget();
        return repository.saveAndFlush(pjp.getArgs()[0]);
        // stop stopwatch
    }
}
