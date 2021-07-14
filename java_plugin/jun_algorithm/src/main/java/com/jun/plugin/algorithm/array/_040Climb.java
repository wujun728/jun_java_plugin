package com.jun.plugin.algorithm.array;
import org.junit.Assert;
import org.junit.Test;

/**
 * 爬楼梯问题:
 * leetCode70：Climbing Stairs
 * 楼梯一共有n级，每次你只能爬1级或者2级。问：从底部爬到顶部一共有多少种不同的路径？
 */
public class _040Climb {
    public int count = 0;

    /**
     * 递归算法
     */
    public int fib01(int n) {
        count++;
        if (n == 1 || n == 2) {
            // System.out.println(n);
            return n;
        } else {
            int k = fib01(n - 1) + fib01(n - 2);
            // System.out.println(n);
            return k;
        }
    }

    /**
     * 递归算法 一行
     */
    public int fib02(int n) {
        return n == 1 || n == 2 ? n : fib02(n - 1) + fib02(n - 2);
    }

    public int dfs(int n, int[] array) {
        if (array[n] != 0) {
            return array[n];
        } else {
            array[n] = dfs(n - 1, array) + dfs(n - 2, array);
            return array[n];
        }
    }

    /**
     * 备忘录法
     */
    public int fib03(int n) {
        if (n == 1 || n == 2) {
            return n;
        } else {
            int[] array = new int[n + 1];
            array[1] = 1;
            array[2] = 2;
            return dfs(n, array);
        }
    }

    /**
     * 动态规划法
     */
    public int fib04(int n) {
        if (n == 1 || n == 2) {
            return n;
        } else {
            int[] array = new int[n + 1];
            array[1] = 1;
            array[2] = 2;
            for (int i = 3; i <= n; i++) {
                array[i] = array[i - 1] + array[i - 2];
            }
            return array[n];
        }
    }

    /**
     * 滚动数组
     */
    public int fib05(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        int a = 1, b = 2, t;
        for (int i = 3; i <= n; i++) {
            t = a + b;
            a = b;
            b = t;
        }
        return b;

    }

    /**
     * 通项公式法
     */
    public int fib06(int n) {
        if (n == 1 || n == 2) {
            return n;
        }
        double sqrtFive = Math.sqrt(5);
        n++;
        double a = Math.pow((1 + sqrtFive) / 2, n);
        double b = Math.pow((1 - sqrtFive) / 2, n);
        double result = 1 / sqrtFive * (a - b);
        return (int) Math.floor(result);

    }

    @Test
    public void test() {
        int n = 15;
        int result = fib01(n);
        System.out.println(result);
        System.out.println(count);
        // 估计上限与下限
        Assert.assertTrue(count <= Math.pow(2, n) && count >= Math.pow(2, n / 2));
    }
}
