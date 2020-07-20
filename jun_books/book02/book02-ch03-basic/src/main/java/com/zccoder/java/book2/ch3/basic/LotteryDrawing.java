package com.zccoder.java.book2.ch3.basic;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <br>
 * 标题: 数组排序<br>
 * 描述: 程序清单3-7<br>
 * 时间: 2018/11/04<br>
 *
 * @author zc
 */
public class LotteryDrawing {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("How many numbers do you need to draw?");
        int k = in.nextInt();

        System.out.println("What is the highest number you can draw?");
        int n = in.nextInt();

        // 初始化数组
        int[] numbers = new int[n];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        int[] result = new int[k];
        for (int i = 0; i < result.length; i++) {

            // 生成 0 至 n-1 中的随机数
            int r = (int) (Math.random() * n);
            // 将随机数作为下标获取numbers中的值并放入result
            result[i] = numbers[r];
            // 将最后一个元素负责给随机数所在位置
            numbers[r] = numbers[n - 1];
            n--;
        }

        // 打印和排序 采用快速排序算法对数组进行排序
        Arrays.sort(result);
        System.out.println("Bet the following combination.It'll make you rich!");
        for (int r : result) {
            System.out.println(r);
        }

    }

}
