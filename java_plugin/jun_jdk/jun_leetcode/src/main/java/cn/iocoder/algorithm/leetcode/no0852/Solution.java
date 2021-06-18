package cn.iocoder.algorithm.leetcode.no0852;

/**
 * https://leetcode-cn.com/problems/peak-index-in-a-mountain-array/
 *
 * 顺序遍历，因为题目默认就存在，先升序，后降序。
 *
 * 最粗暴的方法，时间复杂度是 O(N)
 */
public class Solution {

    public int peakIndexInMountainArray(int[] A) {
        assert A.length >= 3;

        for (int i = 0; i < A.length - 1; i++) {
            if (A[i] > A[i + 1]) {
                return i;
            }
        }

        throw new IllegalArgumentException("根据题目，不存在这个情况");
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.peakIndexInMountainArray(new int[]{0, 1, 0}));
        System.out.println(solution.peakIndexInMountainArray(new int[]{0, 2, 1, 0}));
    }

}
