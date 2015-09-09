package com.huotu.common.model;

/**
 * 1 找回密码
 */
public enum VerificationType implements ICommonEnum {


    BIND_LOGINPASSWORD(1, "找回密码 ");

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
