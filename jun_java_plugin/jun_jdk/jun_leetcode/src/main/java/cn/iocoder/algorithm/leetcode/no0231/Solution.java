package cn.iocoder.algorithm.leetcode.no0231;

/**
 * https://leetcode-cn.com/problems/power-of-two/
 *
 * 位操作
 *
 * 不断和 2^n 次方，进行 & 操作，如果等于 n 自己，则符合条件。
 */
public class Solution {

    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        int power = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & power) == n) {
                return true;
            }
            power = power << 1; // * 2
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.isPowerOfTwo(4));
        System.out.println(solution.isPowerOfTwo(-2147483648));
        for (int i = 0; i <= 16; i++) {
            System.out.println(solution.isPowerOfTwo(i));
        }
    }

}
