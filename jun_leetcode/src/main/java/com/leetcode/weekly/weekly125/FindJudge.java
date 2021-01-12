package com.leetcode.weekly.weekly125;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 找到小镇的法官
 *
 * @author BaoZhou
 * @date 2019/2/24
 */
public class FindJudge {

    @Test
    public void test() {
        int[][] nums = new int[][]{{1, 3}, {1, 4}, {2, 3}, {2, 4}, {4, 3}};
        int[][] nums2 = new int[][]{{1,2},{2,3}};
        System.out.println(findJudge(3, nums2));
    }

    public int findJudge(int N, int[][] trust) {
        int[] in = new int[N + 1];
        int[] out = new int[N + 1];
        for (int i = 0; i < trust.length; i++) {
            out[trust[i][0]] = out[trust[i][0]] + 1;
            in[trust[i][1]] = in[trust[i][1]] + 1;
        }
        System.out.println(Arrays.toString(out));
        System.out.println(Arrays.toString(in));

        int result = -1;
        for (int i = 1; i < in.length; i++) {
            if (in[i] == N - 1 && out[i] == 0) {
                result = i;
            }
        }
        return result;
    }
}
