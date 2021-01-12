package com.leetcode.primary.math;

/**
 * 计数质数
 *
 * @author BaoZhou
 * @date 2018/12/18
 */
public class CountPrimes {
    public static void main(String[] args) {
        System.out.println(countPrimes(10));
    }


    public static int countPrimes(int n) {
        if (n == 0) return 0;
        boolean[] arr = new boolean[n];
        for (int i = 0; i < n; i++) {
            arr[i] = true;
        }

        arr[0] = false;
        int count = 0;
        int limit = (int) Math.sqrt(n);
        // 埃拉托斯特尼筛法
        for (int i = 2; i <= limit; i++) {
            if (arr[i - 1]) {
                // 把能被整除的数字标识出来
                for (int j = i * i; j < n; j += i) {
                    arr[j - 1] = false;
                }
            }
        }

        for (int i = 2; i < n; i++) {
            if (arr[i]) {
                count += 1;
            }
        }
        return count;
    }

}
