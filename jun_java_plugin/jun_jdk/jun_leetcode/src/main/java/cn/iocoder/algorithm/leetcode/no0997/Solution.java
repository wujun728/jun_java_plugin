package cn.iocoder.algorithm.leetcode.no0997;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/squares-of-a-sorted-array/
 *
 * 最粗暴的做法，求和 + 快排
 *
 * 参考博客：https://leetcode-cn.com/problems/squares-of-a-sorted-array/solution/you-xu-shu-zu-de-ping-fang-by-leetcode/
 */
public class Solution {

    public int[] sortedSquares(int[] A) {
        // 平方
        int[] results = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            results[i] = A[i] * A[i];
        }

        // 排序并返回
        Arrays.sort(results);
        return results;
    }

}
