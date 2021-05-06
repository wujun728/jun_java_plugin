package cn.iocoder.algorithm.leetcode.no0169;

/**
 * Boyer-Moore 投票算法
 *
 * 参考博客：https://leetcode-cn.com/problems/majority-element/solution/qiu-zhong-shu-by-leetcode-2/ 方法六。
 *
 * 比较巧妙的方法
 *
 * 1. 假设众数作为 1 ，非众数作为 -1 ，则累加后，结果会是 > 0 。
 * 2. 每次 count 为 0 时候，我们会选择当前数字作为候选的众数，向下求和。
 *      可能候选的众数，实际不是众数，但是最终我们会求到众数。
 *
 * 理解起来会比较抽象，可以自己模拟下。例如说，n 1 0 n n 。
 */
public class Solution02 {

    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.majorityElement(new int[]{2, 1, 2}));
    }

}
