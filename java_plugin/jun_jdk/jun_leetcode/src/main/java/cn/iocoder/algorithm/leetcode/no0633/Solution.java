package cn.iocoder.algorithm.leetcode.no0633;

/**
 * https://leetcode-cn.com/problems/sum-of-square-numbers/
 */
public class Solution {

    public boolean judgeSquareSum(int c) {
        int left = 0;
        int right = (int) Math.sqrt(c);
        while (left <= right) {
            int sum = left * left + right * right;
            if (sum == c) {
                return true;
            }
            if (sum > c) {
                right--;
            } else {
                left++;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.judgeSquareSum(5));
        System.out.println(solution.judgeSquareSum(3));
    }

}
