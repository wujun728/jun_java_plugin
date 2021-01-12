package com.leetcode.weekly.weekly138;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 高度检查器
 *
 * @author: BaoZhou
 * @date : 2019/5/27 9:42
 */
public class HeightChecker {
    @Test
    public void test() {
        int k[] = {1, 1, 4, 2, 1, 3};
        System.out.println(heightChecker(k));
    }


    public int heightChecker(int[] heights) {
        int[] ints = heights.clone();
        Arrays.sort(heights);
        int result = 0;
        for (int i = 0; i < heights.length; i++) {
            if (ints[i] != heights[i]) {
                result++;
            }
        }
        return result;
    }
}
