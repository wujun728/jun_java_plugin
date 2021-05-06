package cn.iocoder.algorithm.leetcode.no0371;

/**
 * https://leetcode-cn.com/problems/sum-of-two-integers/
 *
 * 位操作
 *
 * 这个实现，真的是牛逼啊！
 */
public class Solution {

    public int getSum(int a, int b) {
        // 1、a ^ b ，每一位，求和。 0 0 得 0 ，0 1 得 1 ，1 1 得 0（需要进位）
        // 2.1、a & b ，每一位，进位。0 0 得 0 ，0 1 得 0 ，1 1 得 1 。
        // 2.2、((a & b) << 1) ，进位左移，这样就可以进位累加。继续递归
        // 3、b == 0 ，当 b 等于 0 ，说明，已经累加全部完毕。
        return b == 0 ? a : this.getSum(a ^ b, ((a & b) << 1));
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.getSum(1, 2));
        System.out.println(solution.getSum(-2, 3));
    }

}
