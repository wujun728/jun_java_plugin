package com.zccoder.java.book2.ch3.basic;

import java.util.Scanner;

/**
 * <br>
 * 标题: 先计算退休账户中的余额，然后再询问是否打算退休<br>
 * 描述: 3.8.3<br>
 * 时间: 2018/10/18<br>
 *
 * @author zc
 */
public class Retirement2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("你每年存储多少钱？");
        double payment = in.nextDouble();

        System.out.println("请输入利率（%）：");
        double interestRate = in.nextDouble();

        double balance = 0;
        int years = 0;

        String input;

        do {
            balance = balance + payment;
            double interest = balance * interestRate / 100;
            balance = balance + interest;
            years++;

            System.out.printf("%d 年之后，你的余额有 %,.2f%n", years, balance);

            // 询问是否需要退休
            System.out.print("准备退休？(Y/N)");
            input = in.next();
        } while ("N".equalsIgnoreCase(input));
    }

}
