package com.zccoder.java.book2.ch3.basic;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * <br>
 * 标题: 大数值测试<br>
 * 描述: 程序清单3-6<br>
 * 时间: 2018/11/04<br>
 *
 * @author zc
 */
public class BigIntegerTest {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("How many numbers do you need to draw?");
        int k = in.nextInt();

        System.out.println("What is the highest number you can draw?");
        int n = in.nextInt();

        BigInteger lotteryOdds = BigInteger.valueOf(1L);

        for (int i = 1; i < k; i++) {
            lotteryOdds = lotteryOdds.multiply(BigInteger.valueOf(n - i + 1)).divide(BigInteger.valueOf(i));
        }

        System.out.println("Your odds are 1 in " + lotteryOdds + ". Good luck!");

    }

}
