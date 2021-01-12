package com.designpatterns.bridge;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class SavingAccount implements Account {

    @Override
    public Account openAccount() {
        System.out.println("打开活期帐号");

        return new SavingAccount();
    }

    @Override
    public void showAccountType() {
        System.out.println("这是一个活期帐号");

    }
}
