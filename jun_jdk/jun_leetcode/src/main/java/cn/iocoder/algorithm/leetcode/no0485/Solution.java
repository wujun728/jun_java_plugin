package cn.iocoder.algorithm.leetcode.no0485;

/**
 * https://leetcode-cn.com/problems/max-consecutive-ones/
 */
public class Solution {

    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int counts = 0; // 计数当前序列，有多少个 1 。
        for (int num : nums) {
            if (num == 0) {
                counts = 0;
            } else {
                // 计数++
                counts++;
                // 判断是否最大
                if (counts > max) {
                    max = counts;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1}));
        System.out.println(solution.findMaxConsecutiveOnes(new int[]{1, 1, 1, 0, 1, 1}));
    }

}
