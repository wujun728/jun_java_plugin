package cn.iocoder.algorithm.leetcode.no0069;

/**
 * https://leetcode-cn.com/problems/sqrtx/
 */
public class Solution {

    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }

        int left = 1, right = x;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int result = x / mid; // 使用除法的原因，可能 x 超级大。如果使用乘法，x * x 可能会超过 int 大小。
            // 相等，直接返回
            if (result == mid) {
                return mid;
            }
            // 大 1 ，也直接返回
            if (result == mid + 1) {
                return mid;
            }
            // 小 1 ，也直接返回
            if (result == mid - 1) {
                return result;
            }
            // 如果 result 大，则增大
            if (result > mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        throw new IllegalStateException("不会出现该情况");
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.mySqrt(1));
//        System.out.println(solution.mySqrt(2));
        System.out.println(solution.mySqrt(3));
        System.out.println(solution.mySqrt(4));
        System.out.println(solution.mySqrt(6));
        System.out.println(solution.mySqrt(7));
        System.out.println(solution.mySqrt(8));
        System.out.println(solution.mySqrt(9));
        System.out.println(solution.mySqrt(10));
        System.out.println(solution.mySqrt(11));
        System.out.println(solution.mySqrt(12));
    }

}
