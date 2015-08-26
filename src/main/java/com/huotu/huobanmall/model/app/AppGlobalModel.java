package com.huotu.huobanmall.model.app;

/**
 * 公共信息
 * APP端请无视
 * <p>在服务器实例中 它应当是唯一 并且是可维护的</p>
 * Created by lgh on 2015/8/24.
 */
public class AppGlobalModel {
    /**
     * 服务地址
     */
    private String serverUrl;
    /**
     * 关于我们
     */
    private String aboutURL;
    /**
     * 帮助
     */
    private String helpURL;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAboutURL() {
        return aboutURL;
    }

    public void setAboutURL(String aboutURL) {
        this.aboutURL = aboutURL;
    }

    public String getHelpURL() {
        return helpURL;
    }

    public void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }
}
