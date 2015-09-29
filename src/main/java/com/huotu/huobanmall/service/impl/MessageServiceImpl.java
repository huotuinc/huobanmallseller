package com.huotu.huobanmall.service.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.huotu.common.model.AppOS;
import com.huotu.huobanmall.api.common.Paging;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.AppMessageModel;
import com.huotu.huobanmall.repository.*;
import com.huotu.huobanmall.service.MessageService;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.exceptions.NetworkIOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/6/15.
 */
@Service
public class MessageServiceImpl implements MessageService, AutoCloseable {

    private static final Log log = LogFactory.getLog(MessageServiceImpl.class);
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageToUserRepository toUserRepository;
    @Autowired
    private PushingMessageRepository pushingMessageRepository;

    private JPushClient client;
    @Autowired
    private Environment env;
    private ApnsService service;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @PostConstruct
    public void init() {
        //andriod配置
        String MASTERSECRET = "85ed60eced5cfd1f64fe4203";
        String APPKEY = "a237b5c86ab65f7fa6260bcf";
        client = new JPushClient(MASTERSECRET, APPKEY, env.acceptsProfiles("!test"), 24 * 60 * 60);

        InputStream input;
        if (env.acceptsProfiles("test")) {
            input = MessageService.class.getResourceAsStream("Push.Development.p12");
        } else {
            input = MessageService.class.getResourceAsStream("Push.Production.p12");
        }

        ApnsServiceBuilder builder = APNS.newService().withCert(input, "Iamhuotu");
        if (env.acceptsProfiles("test")) {
            builder = builder.withSandboxDestination();
        } else {
            builder = builder.withProductionDestination();
        }

        service = builder.build();
        service.testConnection();
        service.start();
    }

    @PreDestroy
    public void close() throws Exception {
        if (service != null) {
            service.stop();
        }
    }

    @Override
    @Transactional
    public void sendMessageToMerchant(Message message, Iterable<Merchant> users) {
        //一个消息 不会重复发送
        for (Merchant user : users) {
            MessageToUser toUser = new MessageToUser();
            toUser.setMerchant(user);
            toUser.setDeleted(false);
            toUser.setMessage(message);
            toUser.setReceivedTime(new Date());
            toUserRepository.save(toUser);
        }
    }

    @Override
    @Transactional
    public void sendMessageToOperator(Message message, Iterable<Operator> users) {
        //一个消息 不会重复发送
        for (Operator user : users) {
            MessageToUser toUser = new MessageToUser();
            toUser.setOperator(user);
            toUser.setDeleted(false);
            toUser.setMessage(message);
            toUser.setReceivedTime(new Date());
            toUserRepository.save(toUser);
        }
    }


    @Override
    public void pushMessages() {
        pushingMessageRepository.findAll().forEach(this::pushMessage);
    }


    @Override
    public void pushMessageToAllUser(PushingMessage message) {
        for (final AppOS os : AppOS.values()) {
            Set<String> devices1 = merchantRepository.findDeviceByOS(os);
            Set<String> devices2 = operatorRepository.findDeviceByOS(os);

            if (!devices1.isEmpty()) {
                PushingMessage pushing = message.clone();
                pushing.setDeviceTokens(devices1);
                pushing.setOs(os);
                pushingMessageRepository.save(pushing);
            }

            if (!devices2.isEmpty()) {
                PushingMessage pushing = message.clone();
                pushing.setDeviceTokens(devices2);
                pushing.setOs(os);
                pushingMessageRepository.save(pushing);
            }
        }
    }


    /**
     * 将一条消息 推送到 指定设备
     *
     * @param message 即将被推送的消息
     */
    @Override
    @Transactional
    public boolean pushMessage(PushingMessage message) {

        if (message.getOs() == AppOS.iOS) {
            String payload = APNS.newPayload()
                    .sound("default")
                    .alertBody(message.getTitle())
                    .alertTitle(message.getTitle())
                    .customField("type", message.getType().getValue())
                    .customField("data", message.getData())
                    .customField("username", message.getUsername())
                    .badge(1)
                    .build();

            if (message.getDeviceTokens().isEmpty()) {
                log.debug("无信息可发");
                disposePushingMessage(message);
                return false;
            }

            if (log.isDebugEnabled()) {
                log.debug("准备推送到iOS设备:" + String.join(",", message.getDeviceTokens()));
            }

            try {
                service.push(message.getDeviceTokens(), payload);
                disposePushingMessage(message);
                return true;
            } catch (NetworkIOException ex) {
                log.error("IOS推送错误", ex);
            }
            return false;
        }
        HashMap<String, String> extras = new HashMap<>();
        extras.put("type", "" + message.getType().getValue());
        if (message.getData() != null)
            extras.put("data", message.getData());


        if (message.getUsername() != null)
            extras.put("username", message.getUsername());

        Notification notification = Notification.android(message.getTitle(), message.getTitle(), extras);

        PushPayload.Builder builder = PushPayload.newBuilder().setPlatform(Platform.android());

        if (message.getDeviceTokens() == null || message.getDeviceTokens().isEmpty()) {
            builder = builder.setAudience(Audience.all());

            if (log.isDebugEnabled()) {
                log.debug("准备推送到Android设备：全部");
            }
        } else {
//            if (message.getDeviceTokens().isEmpty()){
//                log.debug("无信息可发");
//                message.getDeviceTokens().clear();
//                pushingMessageRepository.delete(message);
//                return;
//            }
            if (log.isDebugEnabled()) {
                log.debug("准备推送到Android设备:" + String.join(",", message.getDeviceTokens()));
            }

            builder = builder.setAudience(Audience.alias(message.getDeviceTokens()));
        }

        try {
            client.sendPush(builder.setNotification(notification).build());
            disposePushingMessage(message);
            return true;
        } catch (APIConnectionException | APIRequestException e) {
            log.error("推送连接错误", e);
        }
        return false;

    }

    private void disposePushingMessage(PushingMessage message) {
        message.getDeviceTokens().clear();

        try {
            pushingMessageRepository.delete(message);
        } catch (Exception ignored) {
        }
    }

    @Override
    public AppMessageModel[] getMessages(Merchant user, Operator operator, Paging paging) {
        List<MessageToUser> messages = toUserRepository.findAll((root, query, cb) -> {
            Date date = new Date();
            Path<Message> message = root.get("message");
            Predicate all = cb.and(
                    user != null ? cb.equal(root.get("merchant").as(Merchant.class), user) : cb.equal(root.get("operator").as(Merchant.class), operator),
                    cb.equal(message.get("deleted").as(Boolean.class), false),
                    cb.lessThanOrEqualTo(message.get("sendTime"), date),
                    cb.greaterThan(message.get("invalidTime"), date)
            );
            if (paging.getPagingTag() != null) {
                //是否是一个什么什么
                try {
                    Long order = Long.parseLong(paging.getPagingTag());
                    return cb.and(all, cb.lessThan(root.get("id").as(Long.class), order));
                } catch (Exception ignored) {
                }
            }
            return all;
        }, new PageRequest(0, paging.getPagingSize(), new Sort(Sort.Direction.DESC, "id"))).getContent();


        AppMessageModel[] models = new AppMessageModel[messages.size()];
        for (int i = 0; i < messages.size(); i++) {
            AppMessageModel model = new AppMessageModel();
            MessageToUser message = messages.get(i);
            model.setContext(message.getMessage().getContent());
            model.setDate(message.getReceivedTime());
            model.setMessageid(message.getId());
            model.setMessageOrder(message.getId());
            models[i] = model;
        }
        return models;
    }

    @Override
    @Transactional
    public void deleteExceeds() {
        Date date = new Date();
//        toUserRepository.deleteInBatch(toUserRepository.findByMessage_DeletedTrueOrMessage_InvalidTimeLessThan(date));
//        messageRepository.deleteInBatch(messageRepository.findByDeletedTrueOrInvalidTimeLessThan(date));

//        toUserRepository.deleteByMessage_DeletedTrueOrMessage_InvalidTimeLessThan(date);
//        messageRepository.deleteByDeletedTrueOrInvalidTimeLessThan(date);

        toUserRepository.executeHql("delete from MessageToUser toUser where  toUser.message.deleted=true or toUser.message.invalidTime<:date", query -> {
            query.setParameter("date", date);
        });

        messageRepository.executeHql("delete from Message  message where message.deleted=true or message.invalidTime<:date", query -> {
            query.setParameter("date", date);
        });

        log.debug("过期消息已删除");
    }

}
