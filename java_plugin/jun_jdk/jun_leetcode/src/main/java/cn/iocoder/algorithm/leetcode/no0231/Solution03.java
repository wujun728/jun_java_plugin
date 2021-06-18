package cn.iocoder.algorithm.leetcode.no0231;

/**
 * 位操作
 *
 * 如果是 2 的次方，则 n & (n - 1) == 0
 *
 * 性能最优解法
 */
public class Solution03 {

    public boolean isPowerOfTwo(int n) {
        return n > 0
                && (n & (n - 1)) == 0;
    }

}
