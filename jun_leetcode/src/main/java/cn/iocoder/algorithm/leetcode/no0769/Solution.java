package cn.iocoder.algorithm.leetcode.no0769;

/**
 * https://leetcode-cn.com/problems/max-chunks-to-make-sorted/
 */
public class Solution {

    public int maxChunksToSorted(int[] arr) {
        int maxChunks = 0;
        int maxNumber = 0;
        for (int i = 0; i < arr.length; i++) {
            // 求当前的最大值
            maxNumber = Math.max(maxNumber, arr[i]);
            // 当前下标，和 maxNumber 相等，说明前 i 个，在 [0, maxNumber] 内。
            // 注意噢，arr 中，数字是从 0 开始的。
            if (maxNumber == i) {
                maxChunks++;
            }
        }

        return maxChunks;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxChunksToSorted(new int[]{4, 3, 2, 1, 0}));
        System.out.println(solution.maxChunksToSorted(new int[]{1, 0, 2, 3, 4}));
    }

}
