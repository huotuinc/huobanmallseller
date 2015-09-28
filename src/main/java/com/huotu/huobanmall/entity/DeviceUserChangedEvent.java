package com.huotu.huobanmall.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 设备user更变时，做出记录
 */
@Entity
@Cacheable(value = false)
public class DeviceUserChangedEvent extends DeviceEvent {

    /**
     * true表示这是一个注册事件
     */
    private boolean register;
    @ManyToOne
    private Merchant merchant;
    @ManyToOne
    private Operator operator;

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }


    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }
}