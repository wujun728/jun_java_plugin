package com.designpatterns.bridge;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class DepositAccount implements Account {
    @Override
    public Account openAccount() {
        System.out.println("打开定期帐号");
        return new DepositAccount();
    }

    @Override
    public void showAccountType() {
        System.out.println("这是一个定期帐号");
    }
}
