package com.huotu.huobanmall.model.app;

/**
 * 商家信息
 * Created by Administrator on 2015/8/24.
 */
public class AppMerchantModel {
    /**
     * 登录名
     */
    private String name;

    /**
     * 昵称 显示在app中
     */
    private String nickName;

    /**
     * 店铺描述
     */
    private String discription;

    /**
     * 店铺logo
     */
    private String logo;

    private String mobile;
    /**
     * 订单支付成功通知（0关闭,1开启）
     */
    private Number enableBillNotice;
    /**
     * 新增小伙伴通知（0关闭，1开启）
     */
    private Number enablePartnerNotice;

    /**
     * 推送时间 以"开始时间,结束时间"表示 单位秒 默认0,0（不限制） 如 3600,7200
     */
    private String pushTime;

    /**
     * 欢迎提示，包括积分转换信息，来宾转正信息
     */
    private String welcomeTip;
    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递
     * <b>每次App获得新的Token,旧Token就弃用。</b>
     */
    private String token;

}
