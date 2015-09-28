package com.huotu.huobanmall.concurrency;

import com.huotu.huobanmall.config.CommonEnum;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.MessageService;
import com.huotu.huobanmall.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.jpa.JpaCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Cache;
import javax.persistence.EntityManagerFactory;
import java.util.Date;

/**
 * @author CJ
 */
@Service
public class SystemMaintaining {


    private static final Log log = LogFactory.getLog(SystemMaintaining.class);
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private Environment env;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private PushingMessageRepository pushingMessageRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    /**
     * 每隔10秒 推送需要推送的所有消息
     */
    @Scheduled(initialDelay = 60000, fixedDelay = 10000)
    public void pushMessages() {
        messageService.pushMessages();
    }


    private int testRuns;
    private int testRuns2 = 2;

    @Scheduled(initialDelay = 5000, fixedDelay = 60000)
    @Transactional
    public void work() {
        messageService.deleteExceeds();

//        if (env.acceptsProfiles("test")) {
            log.info("周期性清理缓存");
            Cache cache = entityManagerFactory.getCache();
            if (cache != null && cache instanceof JpaCache) {
                ((JpaCache) cache).clear();
            }
            if (cache != null)
                cache.evictAll();

            log.info("自动发送站内消息");
            //在测试期间 每一分钟 发一条垃圾消息
            Message message = new Message();

            //马上发送 1小时以后过期
            message.setInvalidTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000));
            message.setSendTime(new Date());
            message.setAddedTime(new Date());
            message.setTitle("都是蒋才干的");
            message.setContent("兄弟们辛苦了，这是来自那美克星的问候！");

            messageRepository.save(message);

            messageService.sendMessageToMerchant(message, merchantRepository.findAll());
            messageService.sendMessageToOperator(message, operatorRepository.findAll());


            //周期性发送推送
            if (testRuns++%5==0){
                PushingMessage msg = new PushingMessage();
                msg.setType(CommonEnum.PushMessageType.Notify);
                msg.setTitle("吾夜观天象，发现已经"+new Date());
                messageService.pushMessageToAllUser(msg);
            }
            if(testRuns2++%5==0){
                PushingMessage msg = new PushingMessage();
                msg.setType(CommonEnum.PushMessageType.RemindMessage);
                msg.setTitle("大丈夫当带三尺之剑，立不世之功！");
                messageService.pushMessageToAllUser(msg);
            }
//        }
    }

}
