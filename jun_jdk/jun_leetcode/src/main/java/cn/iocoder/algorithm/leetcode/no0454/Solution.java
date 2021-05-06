package cn.iocoder.algorithm.leetcode.no0454;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/4sum-ii/
 *
 * 哈希表
 */
public class Solution {

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        // 先求，A 和 B 的和的组合
        Map<Integer, Integer> sumAB = new HashMap<>();
        for (int a : A) {
            for (int b : B) {
                int sum = a + b;
                sumAB.put(sum, sumAB.getOrDefault(sum, 0) + 1);
            }
        }

        // 再求，C 和 D 的组合
        int counts = 0;
        for (int c : C) {
            for (int d : D) {
                int sum = c + d;
                counts += sumAB.getOrDefault(-sum, 0);
            }
        }

        return counts;
    }

}
