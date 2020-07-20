package com.zccoder.java.book2.ch3.basic;

/**
 * <br>
 * 标题: 演示三角形数组<br>
 * 描述: 程序清单3-9<br>
 * 时间: 2018/11/04<br>
 *
 * @author zc
 */
public class LotteryArray {

    public static void main(String[] args) {

        final int rowMax = 10;

        int[][] odds = new int[rowMax + 1][];
        for (int n = 0; n <= rowMax; n++) {
            odds[n] = new int[n + 1];
        }

        for (int n = 0; n < odds.length; n++) {
            for (int k = 0; k < odds[n].length; k++) {
                // n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
                int lotteryOdds = 1;
                for (int i = 1; i <= k; i++) {
                    lotteryOdds = lotteryOdds * (n - i + 1) / i;
                }
                odds[n][k] = lotteryOdds;
            }
        }

        // 打印三角形数组
        for (int[] row : odds) {
            for (int odd : row) {
                System.out.printf("%4d", odd);
            }
            System.out.println();
        }

    }

}
