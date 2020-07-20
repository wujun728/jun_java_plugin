package com.zccoder.java.book2.ch3.basic;

import java.util.Scanner;

/**
 * <br>
 * 标题: 从 1~50 之间的数字中取6个数字来抽奖<br>
 * 描述: 3.8.4<br>
 * 时间: 2018/10/18<br>
 *
 * @author zc
 */
public class LotteryOdds {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("取几个数字来抽奖？");
        int k = in.nextInt();

        System.out.println("从1至最大的数是多少？ ");
        int n = in.nextInt();

        int lotteryOdds = 1;
        for (int i = 1; i <= k; i++) {
            lotteryOdds = lotteryOdds * (n - i + 1) / i;
        }

        System.out.println("中奖的几率为 1/" + lotteryOdds + ". 祝你好运!");

    }

}
