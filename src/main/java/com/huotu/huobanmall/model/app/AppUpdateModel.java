package com.huotu.huobanmall.model.app;

import com.huotu.huobanmall.config.CommonEnum;

/**
 * 更新信息
 * Created by Administrator on 2015/8/24.
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
