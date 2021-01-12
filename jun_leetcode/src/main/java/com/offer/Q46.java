package com.offer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;


/**
 * 孩子们的游戏(圆圈中最后剩下的人)
 * f(n,m) = (f(n-1,m) + m) % n
 * f(n-1,m) + m ：上一轮停在的位置 再继续报数
 * @author BaoZhou
 * @date 2020-6-24
 */

public class Q46 {
    @Test
    public void result() {
        System.out.println(LastRemaining_Solution(4, 3));
    }


    public int LastRemaining_Solution(int n, int m) {
        if (n <= 0) return -1;
        int index = 0;
        for (int i = 2; i <= n; ++i) {
            index = (index + m) % i;
        }
        return index;
    }
}



