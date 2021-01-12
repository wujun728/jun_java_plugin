package com.leetcode.weekly.weekly132;

import org.junit.jupiter.api.Test;

/**
 * 除数博弈
 *
 * @author BaoZhou
 * @date 2019/5/13
 */
public class DivisorGame {
    @Test
    public void test() {
        System.out.println(divisorGame(5));
    }

    public boolean divisorGame(int N) {
        return N % 2 == 0;
    }
}
