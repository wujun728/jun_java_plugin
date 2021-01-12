package com.designpatterns.bridge;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class ICBCBank extends Bank {
    public ICBCBank(Account account) {
        super(account);
    }

    @Override
    Account openAccount() {
        System.out.println("打开中国工商银行帐号");
        account.openAccount();
        return account;
    }
}
