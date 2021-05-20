package cn.iocoder.algorithm.leetcode.no0231;

/**
 * 位操作
 *
 * 通过 n & (n - 1) 的方式，不断去掉最末位(右边)的 1 。
 * 如果超过 1 个 1 ，则说明是非 2 的次方。
 */
public class Solution02 {

    public boolean isPowerOfTwo(int n) {
        if (n <= 0) {
            return false;
        }
        int count = 0;
        while (n > 0) {
            n = n & (n - 1);
            count++;
            if (count >= 2) {
                break;
            }
        }
        return count == 1;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.isPowerOfTwo(1));
//        System.out.println(solution.isPowerOfTwo(4));
//        System.out.println(solution.isPowerOfTwo(-2147483648));
        for (int i = 0; i <= 16; i++) {
            System.out.println(solution.isPowerOfTwo(i));
        }
    }

}
