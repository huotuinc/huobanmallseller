package com.huotu.huobanmall.service;

import com.huotu.huobanmall.api.common.Paging;
import com.huotu.huobanmall.entity.*;
import com.huotu.huobanmall.model.app.AppMessageModel;
import org.springframework.transaction.annotation.Transactional;

/**
 * 伙伴商城消息
 * Created by Administrator on 2015/6/15.
 */
public interface MessageService {

    /**
     * 发送站内消息给收件人
     * @param message
     * @param users
     */
    @Transactional
    void sendMessageToMerchant(Message message, Iterable<Merchant> users);

    /**
     * 发送站内消息给收件人
     * @param message
     * @param users
     */
    @Transactional
    void sendMessageToOperator(Message message, Iterable<Operator> users);


//    /**
//     * 推送消息
//     *
//     * @param messageId 消息id
//     * @param alias     别名列表
//     * @param extras    自定义参数
//     * @param userList  用户列表
//     * @return
//     */
//    int sendMessageToMerchant(int messageId, List<String> alias, Map<String, String> extras, List<Long> userList);

    /**
     * 推送消息
     * <p>成功以后将被删除</p>
     * @param message  待推送的消息
     * @return true 表示成功推送
     */
    @Transactional
    boolean pushMessage(PushingMessage message);

    /**
     * 消息列表
     *
     * @param user 商家
     * @param operator 操作员
     * @param paging 排序
     * @return
     */
    AppMessageModel[] getMessages(Merchant user,Operator operator, Paging paging);

    /**
     * 删除无用信息
     */
    @Transactional
    void deleteExceeds();

    /**
     * 立即推送未推送的消息
     */
    @Transactional
    void pushMessages();

    /**
     * 立即推送该消息到所有用户
     * @param message 待发送消息
     */
    @Transactional
    void pushMessageToAllUser(PushingMessage message);
}
