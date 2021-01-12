package com.leetcode.weekly.weekly137;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 最后一块石头的重量
 *
 * @author: BaoZhou
 * @date : 2019/5/19 10:31
 */
public class LastStoneWeight {
    @Test
    public void test() {
        int k[] = {10,10,7,2};
        System.out.println(lastStoneWeight(k));
    }

    public int lastStoneWeight(int[] stones) {
        int length = stones.length;
        for (int i = 1; i < length; i++) {
            Arrays.sort(stones);
            stones[length - i - 1] = stones[length - i] - stones[length - i - 1];

        }
        return stones[0];

    }
}
