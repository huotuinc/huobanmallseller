/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.test.base;

import com.huotu.common.model.AppOS;

/**
 * Created by CJ on 5/30/15.
 *
 * @author CJ
 */
public enum DeviceType {

    Android,iOS;

    public AppOS toOS(){
        switch (this){
            case Android:
                return AppOS.Android;
            case iOS:
                return AppOS.iOS;
            default:
                return AppOS.WindowsPhone;
        }
    }


}
