package com.huotu.huobanmall.model.app;

/**
 * Created by lgh on 2015/8/25.
 */
public class AppPublicModel {

    /**
     * 服务端参数
     * <p>当前用户，如果没有则为空</p>
     */
//    private Merchant currentUser;
//    /**
//     * 服务端参数
//     * <p>当前用户设备，必然非空</p>
//     */
//    private Device currentDevice;
    /**
     * 平台1、FM2015AD   （android）    2、FM2015AP   （ios)
     */
    private String operation;
    /**
     * 版本号
     */
    private String version;
    /**
     * 设备号 设备必须确保唯一 也必须确保每次发送给服务端都是一致的
     */
    private String imei;
    /**
     * 验证签名
     */
    private String sign;
    /**
     * b73ca64567fb49ee963477263283a1bf
     */
    private String appKey;

    /**
     * 百度cityCode
     */
    private String cityCode;
    /**
     * 毫秒--utc
     */
    private long timestamp;
    /**
     * 身份验证  服务端负责生成 负责审核
     */
    private String token;
    /**
     * 渠道编号 默认default
     */
    private String cpaCode;
    /**
     * ip
     */
    private String ip;
}
