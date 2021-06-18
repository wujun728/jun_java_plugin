package cn.iocoder.algorithm.leetcode.no0260;

/**
 * https://leetcode-cn.com/problems/single-number-iii/
 *
 * 位操作
 *
 * 基于 ^ 异或操作
 */
public class Solution {

    public int[] singleNumber(int[] nums) {
        // 异或操作所有数字，最终结果是不重复的两个数字的 ^ 操作结果。
        int sign = 0;
        for (int num : nums) {
            sign = sign ^ num;
        }

        // 获取最低位第 1 个 1 。因为这两个数字 ^ 操作的结果，只会有不相同的位，才会获得 1 。
        // 这样，我们获取到最低的第 1 个 1 ，就足够实现这两个数字的判断了。
        sign = sign & (-sign);

        // 求解
        int[] result = new int[2];
        for (int num : nums) {
            if ((num & sign) == sign) { // 通过 & 操作，可以计算出第一个数字，和恰好能满足 sign 的数字们。因为其他数字，恰好能满足每个是 2 个，就被 ^ 操作抵消
                result[0] = result[0] ^ num; // 这里使用 result[0] 的原因，只是作为一个分类。
            } else {
                result[1] = result[1] ^ num;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if (false) {
            int a = 3;
            int b = 5;
            int c = a ^ b;
            int d = c & -c;
            System.out.println(c);
            System.out.println(d);
        }
        System.out.println();
    }

}
