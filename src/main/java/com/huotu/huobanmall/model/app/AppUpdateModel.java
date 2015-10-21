/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanmall.model.app;

import com.huotu.huobanmall.config.CommonEnum;

/**
 * 更新信息
 * Created by lgh on 2015/8/24.
 */
public class AppUpdateModel {
    private String updateMD5;
    private String updateUrl;
    private String updateTips;
    private CommonEnum.VersionUpdateType updateType;

    public String getUpdateMD5() {
        return updateMD5;
    }

    public void setUpdateMD5(String updateMD5) {
        this.updateMD5 = updateMD5;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public CommonEnum.VersionUpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(CommonEnum.VersionUpdateType updateType) {
        this.updateType = updateType;
    }

}
