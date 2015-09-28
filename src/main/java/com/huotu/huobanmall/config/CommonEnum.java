package com.huotu.huobanmall.config;


import com.huotu.huobanmall.common.model.ICommonEnum;

/**
 * 枚举类
 *
 * @author Administrator
 */
public interface CommonEnum {


    //todo 待整理
    enum AppCode implements ICommonEnum {

        /**
         * SUCCESS(1, "操作成功"),
         */
        SUCCESS(1, "操作成功"),

        /**
         * DBNULL(5000, "暂无数据"),
         */
        DBNULL(5000, "暂无数据"),

        /**
         * SYSTEM_BAD_REQUEST(50001, "系统请求失败"),
         */
        SYSTEM_BAD_REQUEST(50001, "系统请求失败"),

        /**
         * SYSTEM_BUSY(50002, "系统繁忙")
         */
        SYSTEM_BUSY(50002, "系统繁忙"),

        /**
         * SYSTEM_BAD_PARAMETER(50003, "系统请求参数错误")
         */
        SYSTEM_BAD_PARAMETER(50003, "系统请求参数错误"),

        /**
         * SYSTEM_BAD_VERSION(50004, "请更新到最新版本，才能使用")
         */
        SYSTEM_BAD_VERSION(50004, "请更新到最新版本，才能使用"),

        /**
         * SYSTEM_BAD_ACCOUNT(50005, "因你的账户涉嫌恶意刷分，已被冻结，如有疑问请联系客服")
         */
        SYSTEM_BAD_ACCOUNT(50005, "因你的账户涉嫌恶意刷分，已被冻结，如有疑问请联系客服"),

        /**
         * SYSTEM_BAD_IMEI(50006, "无法读取手机识别码，请重新启动程序")
         */
        SYSTEM_BAD_IMEI(50006, "无法读取手机识别码，请重新启动程序"),

        /**
         * SYSTEM_BAD_MOBILE_TIME(50007, "手机时间错误，请更正后重新操作")
         */
        SYSTEM_BAD_MOBILE_TIME(50007, "手机时间错误，请更正后重新操作"),

        /**
         * SYSTEM_NONSUPPORT_VERSION(50008, "粉猫版本过低，请升级到最新版本后再操作")
         */
        SYSTEM_NONSUPPORT_VERSION(50008, "粉猫版本过低，请升级到最新版本后再操作"),

        SYSTEM_BAD_REQUEST_50601(50601, "系统请求失败,code 50601"),//sign校验失败

        SYSTEM_BAD_REQUEST_50602(50602, "系统请求失败,code 50602"),

        SYSTEM_BAD_REQUEST_50603(50603, "系统请求失败,code 50603"),

        SYSTEM_BAD_REQUEST_50604(50604, "系统请求失败,code 50604"),

        SYSTEM_BAD_REQUEST_50605(50605, "系统请求失败,code 50605"),

        /**
         * ERROR_WRONG_USERNAME(53001, "不合法的用户名")
         */
        ERROR_WRONG_USERNAME(53001, "不合法的用户名"),

        /**
         * ERROR_WRONG_PASSWORD(53002, "不合法的用户密码")
         */
        ERROR_WRONG_PASSWORD(53002, "不合法的用户密码"),

        /**
         * ERROR_WRONG_MOBILE(53003, "不合法的手机号")
         */
        ERROR_WRONG_MOBILE(53003, "不合法的手机号"),

        /**
         * ERROR_WRONG_ALIPAY_NO(53004, "支付宝号格式不正确")
         */
        ERROR_WRONG_ALIPAY_NO(53004, "支付宝号格式不正确"),

        /**
         * ERROR_WRONG_ALIPAY_NAME(53005, "不合法的支付宝姓名")
         */
        ERROR_WRONG_ALIPAY_NAME(53005, "不合法的支付宝姓名"),

        /**
         * ERROR_VERIFICATION_CODE(53007, "错误的验证码")
         */
        ERROR_VERIFICATION_CODE(53007, "错误的验证码"),

        /**
         * ERROR_WRONG_WITHDRAWAL_PASSWORD(53008, "错误的提现密码")
         */
        ERROR_WRONG_WITHDRAWAL_PASSWORD(53008, "错误的提现密码"),

        /**
         * ERROR_WRONG_NAME(53009, "错误的提现密码")
         */
        ERROR_WRONG_NAME(53009, "错误的提现密码"),

        /**
         * ERROR_WRONG_DATE(53010, "不合法的日期")
         */
        ERROR_WRONG_DATE(53010, "不合法的日期"),

        /**
         * ERROR_WRONG_USERNAME_PASSWORD(53011, "错误的用户名或密码")
         */
        ERROR_WRONG_USERNAME_PASSWORD(53011, "错误的用户名或密码"),

        /**
         * ERROR_WRONG_VERIFICATION_TYPE(53012, "不合法的验证码类型")
         */
        ERROR_WRONG_VERIFICATION_TYPE(53012, "不合法的验证码类型"),

        /**
         * ERROR_WRONG_OLD_WITHDRAWAL_PASSWORD(53013, "错误的旧提现密码")
         */
        ERROR_WRONG_OLD_WITHDRAWAL_PASSWORD(53013, "错误的旧提现密码"),

        /**
         * ERROR_WRONG_VERIFICATION_INTERVAL(53014, "验证码发送间隔为90秒")
         */
        ERROR_WRONG_VERIFICATION_INTERVAL(53014, "验证码发送间隔为90秒"),

        /**
         * ERROR_VERIFICATION_CODE_OVERDUE(53015, "验证码已过期，请重新获取验证码")
         */
        ERROR_VERIFICATION_CODE_OVERDUE(53015, "验证码已过期，请重新获取验证码"),

        /**
         * ERROR_WRONG_TASKID(53016, "不合法的任务ID")
         */
        ERROR_WRONG_TASKID(53016, "不合法的任务ID"),

        /**
         * ERROR_HAVE_REWARDED(53017, "您已经领取了该奖励")
         */
        ERROR_HAVE_REWARDED(53017, "您已经领取了该奖励"),

        /**
         * ERROR_NO_CHANCE(53018, "您的答题机会已用完")
         */
        ERROR_NO_CHANCE(53018, "您的答题机会已用完"),

        /**
         * ERROR_ANSWER_ERROR(53019, "答题正确率过低，任务失败")
         */
        ERROR_ANSWER_ERROR(53019, "答题正确率过低，任务失败"),

        /**
         * ERROR_EXIST_SAME_MOBILE(54001, "手机号已经被注册，请选择其他手机号")
         */
        ERROR_EXIST_SAME_MOBILE(54001, "手机号已经被注册，请选择其他手机号"),

        /**
         * ERROR_EXIST_SAME_USERNAME(54002, "存在的相同的用户名")
         */
        ERROR_EXIST_SAME_USERNAME(54002, "存在的相同的用户名"),

        /**
         * ERROR_NO_EXIST_USERNAME(54003, "用户名不存在")
         */
        ERROR_NO_EXIST_USERNAME(54003, "用户名不存在"),

        /**
         * ERROR_EXIST_EMEI(54004, "您的手机已经注册过粉猫，不允许再注册，请直接登录")
         */
        ERROR_EXIST_EMEI(54004, "您的手机已经注册过粉猫，不允许再注册，请直接登录"),

        /**
         * ERROR_EXIST_ALIPAY(54005, "支付宝已经被别的账号绑定")
         */
        ERROR_EXIST_ALIPAY(54005, "支付宝已经被别的账号绑定"),

        /**
         * ERROR_HAVE_SIGNIN(54006, "您今天已经签过到了，明天再来吧")
         */
        ERROR_HAVE_SIGNIN(54006, "您今天已经签过到了，明天再来吧"),

        /**
         * ERROR_CHECKCOUT(54007, "兑换流量失败")
         */
        ERROR_CHECKCOUT(54007, "兑换流量失败"),

        /**
         * ERROR_NO_ENOUGH_FLOW(54008, "您的剩余流量不够本次兑换")
         */
        ERROR_NO_ENOUGH_FLOW(54008, "您的剩余流量不够本次兑换"),

        ERROR_NO_LIFT_FLOW(54009, "您还没有达到起提流量"),
        /**
         * ERROR_NO_USERHEAD(54010, "获取用户头像信息失败")
         */
        ERROR_NO_USERHEAD(54010, "获取用户头像信息失败"),

        ERROR_NO_ENOUGH_REMAINFLOW(54008, "您的剩余流量不足"),
        /**
         * ERROR_SEND_MESSAGE_FAIL(55001, "短信发送通道不稳定，请重新尝试")
         */
        ERROR_SEND_MESSAGE_FAIL(55001, "短信发送通道不稳定，请重新尝试"),

        /**
         * ERROR_USER_LOGIN_FAIL(56000, "用户登录失败")
         */
        ERROR_USER_LOGIN_FAIL(56000, "用户登录失败"),
        /**
         * ERROR_USER_TOKEN_FAIL(56001, "用户登录失效，需要重新登录")
         */
        ERROR_USER_TOKEN_FAIL(56001, "你已经在其他设备登录，需要重新登录"),
        /**
         * ERROR_USER_LOGIN_REQUIRED(57000, "需要登录")
         */
        ERROR_USER_LOGIN_REQUIRED(57000, "需要登录"),

        WRONG_USERNAME(57001, "用户名错误"),

        WRONG_PASSWORD(57002, "密码错误"),

        ILLEGAL_USER(57003, "该账号不可用");


        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        AppCode(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 0 无更新 1增量更新 2 整包更新 3 强制增量更新 4 强制整包更新
     */
    enum VersionUpdateType implements ICommonEnum {

        NO(0, "无更新"),

        INCREMENT(1, "增量更新"),

        WHOLE(2, "整包更新"),

        FORCE_INCREMENT(3, "强制增量更新"),

        FORCE_WHOLE(4, "强制整包更新");

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        VersionUpdateType(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }


    /**
     * 1 注册 2 找回密码 3 绑定手机
     */
    enum VerificationType implements ICommonEnum {

        BIND_REGISTER(1, "注册"),

        BIND_LOGINPASSWORD(2, "找回密码 "),

        BIND_MOBILE(3, "绑定手机");

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        VerificationType(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    enum CodeType implements ICommonEnum {
        /**
         * text(0, "文本")
         */
        text(0, "文本"),
        /**
         * voice(1, "语音")
         */
        voice(1, "语音");

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        CodeType(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 推送消息类型
     */
    enum PushMessageType implements com.huotu.common.model.ICommonEnum {
         /**
         * 1:版本更新
         * <p>携带数据为版本更新摘要</p>
         */
        Version(1, "版本更新"),
        /**
         * 2:消息提醒
         * <p>提示阅读消息，携带数据为空</p>
         */
        RemindMessage(2, "消息提醒"),
        /**
         * 3:通知
         * <p>携带数据为纯文本消息</p>
         */
        Notify(3, "通知");

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        PushMessageType(int value, String name) {
            this.value = value;
            this.name = name;
        }

    }
}
