/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.common.model;

/**
 * @author CJ
 */
public enum CodeType implements ICommonEnum {
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
