package com.huotu.common.model;

/**
 * 1 注册 2 找回密码 3 绑定手机
 */
public enum VerificationType implements ICommonEnum {

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
