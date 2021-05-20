package cn.iocoder.algorithm.leetcode.no0768;

/**
 * https://leetcode-cn.com/problems/max-chunks-to-make-sorted-ii/
 *
 * 参考博客：https://www.jianshu.com/p/014c4d89f7ff
 */
public class Solution {

    public int maxChunksToSorted(int[] arr) {
        // 求，从左到右的最大值
        int[] leftMax = new int[arr.length];
        leftMax[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], arr[i]);
        }

        // 求，从右到左的最小值
        int[] rightMin = new int[arr.length];
        rightMin[arr.length - 1] = arr[arr.length - 1];
        for (int i = arr.length - 2; i >= 0; i--) {
            rightMin[i] = Math.min(arr[i], rightMin[i + 1]);
        }

        // 计算块
        int maxChunks = 0; // 注意，maxChunks 这里指的是多少个间隔。
        for (int i = 0; i < arr.length - 1; i++) { // 因为是求间隔，所以，下面的遍历，arr.length 就多减了 1 。
            if (leftMax[i] <= rightMin[i + 1]) { // 因为是求间隔，所以，这里就是对比，i + 1 个。
                maxChunks++;
            }
        }

        return maxChunks + 1; // 因为是求间隔，所以，这里的结果，就多加了 1 。
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxChunksToSorted(new int[]{5, 4, 3, 2, 1}));
        System.out.println(solution.maxChunksToSorted(new int[]{2, 1, 3, 4, 4}));
    }

}
