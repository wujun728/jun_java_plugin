package com.designpatterns.bridge;

import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2018/12/29
 */
public class BridgeTest {
    @Test
    void test(){
        Bank icbcBank = new ICBCBank(new DepositAccount());
        Account account = icbcBank.openAccount();
        account.showAccountType();

        Bank icbcBank2 = new ICBCBank(new SavingAccount());
        Account account2 = icbcBank.openAccount();
        account2.showAccountType();

        Bank abcBank = new ABCBank(new DepositAccount());
        Account account3 = icbcBank.openAccount();
        account3.showAccountType();

        Bank abcBank2 = new ABCBank(new SavingAccount());
        Account account4 = icbcBank.openAccount();
        account4.showAccountType();
    }
}
