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
