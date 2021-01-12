package com.leetcode.weekly.weekly128;

import org.junit.jupiter.api.Test;

/**
 * 总持续时间可被 60 整除的歌曲
 *
 * @author BaoZhou
 * @date 2019/2/24
 */
public class NumPairsDivisibleBy60 {

    @Test
    public void test() {
        int time[] = {30, 20, 150, 100, 40};
        System.out.println(numPairsDivisibleBy60(time));
    }

    public int numPairsDivisibleBy60(int[] time) {
        int result = 0;
        for (int i = 0; i < time.length-1; i++) {
            for (int j = i+1; j < time.length; j++) {
                if ((time[i] + time[j]) % 60 == 0) {
                    result++;
                }
            }
        }
        return result;
    }

}
