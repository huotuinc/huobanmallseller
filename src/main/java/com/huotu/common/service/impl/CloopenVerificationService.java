package com.huotu.common.service.impl;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.huotu.common.exceptions.InterrelatedException;

import com.huotu.common.model.CodeType;
import com.huotu.huobanmall.entity.VerificationCode;
import com.huotu.huobanmall.model.app.AppGlobalModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author CJ
 */
@Profile("cloopenEnabled")
@Service
public class CloopenVerificationService extends AbstractVerificationService {

    private static final Log log = LogFactory.getLog(CloopenVerificationService.class);

    // 目前还是一些demo帐号数据
    String sid = "8a48b5514e0b153e014e1602e15b0567";
    String token = "868cf3ddbd8541da9f050c252995634b";
    String appid = "8a48b5514e0b153e014e16045937056a";

    private CCPRestSDK restAPI = new CCPRestSDK();
    @Autowired
    private AppGlobalModel appGlobalModel;
    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        log.info("云通讯平台使用中……");
        if (env.acceptsProfiles("test"))
            restAPI.init("sandboxapp.cloopen.com", "8883");
        else
            restAPI.init(env.getProperty("cloopen.endpoint", "sandboxapp.cloopen.com"), "8883");
        // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://

        if (env.acceptsProfiles("test")) {
            restAPI.setAccount(sid, token);// 初始化主帐号名称和主帐号令牌

            restAPI.setAppId(appid);
        } else {
            restAPI.setAccount(env.getProperty("cloopen.sid", sid), env.getProperty("cloopen.token", token));// 初始化主帐号名称和主帐号令牌
            restAPI.setAppId(env.getProperty("cloopen.appid", appid));
        }
    }

    @Override
    protected void doSend(VerificationProject project, VerificationCode code) throws InterrelatedException {
        if (code.getCodeType() == CodeType.text) {
            HashMap<String, Object> result =restAPI.sendTemplateSMS(code.getMobile(),env.acceptsProfiles("test")?"1":project.getTemplateId(),new String[]{code.getCode()});
//            String context = new Formatter(Locale.CHINA).format(project.getFormat(),code.getCode()).toString();
//            HashMap<String, Object> result = restAPI.sendSMS(code.getMobile(), context);
            String status = result.get("statusCode").toString();
            if (!"000000".equals(status)) {
                throw new InterrelatedException("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            }
        } else if (code.getCodeType() == CodeType.voice) {
            HashMap<String, Object> result = restAPI.voiceVerify(code.getCode(), code.getMobile(), appGlobalModel.getCustomerServicePhone(), "3", "");
            String status = result.get("statusCode").toString();
            if (!"000000".equals(status)) {
                throw new InterrelatedException("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            }
        }
    }

    @Override
    public boolean supportVoice() {
        return true;
    }
}
