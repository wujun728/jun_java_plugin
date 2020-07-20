package com.zccoder.java.book2.ch3.basic;

import java.util.Scanner;

/**
 * <br>
 * 标题: 计算需要多长时间才能够存储一定数量的退休金<br>
 * 描述: 3.8.3<br>
 * 时间: 2018/10/18<br>
 *
 * @author zc
 */
public class Retirement {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("你退休需要多少钱？");
        double goal = in.nextDouble();

        System.out.println("你每年存储多少钱？");
        double payment = in.nextDouble();

        System.out.println("请输入利率（%）：");
        double interestRate = in.nextDouble();

        double balance = 0;
        int years = 0;

        while (balance < goal) {
            balance = balance + payment;
            double interest = balance * interestRate / 100;
            balance = balance + interest;
            years++;
        }

        System.out.println("你需要存 " + years + " 年。");
    }

}
