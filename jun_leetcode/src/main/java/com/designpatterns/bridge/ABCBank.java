package com.designpatterns.bridge;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class ABCBank extends Bank {
    public ABCBank(Account account) {
        super(account);
    }

    @Override
    Account openAccount() {
        System.out.println("打开中国农业银行帐号");
        account.openAccount();
        return account;
    }
}
