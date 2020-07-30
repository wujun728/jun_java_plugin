package cn.iocoder.algorithm.leetcode.no0693;

/**
 * https://leetcode-cn.com/problems/binary-number-with-alternating-bits/
 *
 * 位操作
 *
 * ^ 异或操作
 */
public class Solution {

    public boolean hasAlternatingBits(int n) {
        // n 去掉最低位(最右) ，然后异或操作。
        int result = n ^ (n >> 1);
        // 如果满足条件，位就是 1111 的这样形式。这样，我们 result & (result + 1) == 0 ，相当于二进制
        return (result & (result + 1)) == 0;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.hasAlternatingBits(4));
        System.out.println(solution.hasAlternatingBits(7));
        System.out.println(solution.hasAlternatingBits(5)); // true
        System.out.println(solution.hasAlternatingBits(11));
        System.out.println(solution.hasAlternatingBits(10)); // true
    }

}
