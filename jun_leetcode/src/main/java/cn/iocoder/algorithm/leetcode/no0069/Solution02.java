package cn.iocoder.algorithm.leetcode.no0069;

public class Solution02 {

    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }

        int left = 1, right = x;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int result = x / mid;
            // 相等，直接返回
            if (result == mid) {
                return mid;
            }
            // 如果 result 大，则增大
            if (result > mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
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
