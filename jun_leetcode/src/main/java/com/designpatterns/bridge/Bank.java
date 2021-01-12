package com.designpatterns.bridge;

/**
 * 桥接模式
 * @author BaoZhou
 * @date 2018/12/29
 */
public abstract class Bank {
    protected Account account;

    public Bank(Account account) {
        this.account = account;
    }

    abstract Account openAccount();
}
