package cn.iocoder.algorithm.leetcode.no0997;

import java.util.Arrays;

/**
 * {@link Solution02} 的基础上，查找 left 和 right ，使用二分查找
 */
public class Solution03 {

    public int[] sortedSquares(int[] A) {
        // 寻找最后一个负数
        int left;
        int right;
        if (A[0] >= 0) { // 不存在，就不要二分了
            left = -1;
            right = 0;
        } else if (A[A.length - 1] < 0) {
            left = A.length - 1;
            right = A.length;
        } else {
            left = 0;
            right = A.length - 1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (A[mid] < 0 && A[mid + 1] >= 0) { // 不需要判断 mid + 1 会越界，因为上面已经处理掉，一定会存在
                    left = mid;
                    break;
                }
                if (A[mid] >= 0) { // 这里要注意，要 >= 0 ，因为 0 归到 right 里
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            right = left + 1;
        }

        // 开始计算
        int[] results = new int[A.length];
        int idx = 0;
        while (left >= 0 && right < A.length) {
            int leftResult = A[left] * A[left];
            int rightResult = A[right] * A[right];
            if (leftResult > rightResult) {
                results[idx++] = rightResult;
                right++;
            } else {
                results[idx++] = leftResult;
                left--;
            }
        }

        // 上述比较，可能 left 和 right 还没用完。继续
        while (left >= 0) {
            results[idx++] = A[left] * A[left];
            left--;
        }
        while (right < A.length) {
            results[idx++] = A[right] * A[right];
            right++;
        }

        return results;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
//        System.out.println(Arrays.toString(solution.sortedSquares(new int[]{0, 2})));
//        System.out.println(Arrays.toString(solution.sortedSquares(new int[]{-2, -1, 3})));
//        System.out.println(Arrays.toString(solution.sortedSquares(new int[]{-2, -1})));
//        System.out.println(Arrays.toString(solution.sortedSquares(new int[]{1, 4, 9})));
        System.out.println(Arrays.toString(solution.sortedSquares(new int[]{-4, -1, 0, 3, 10})));
    }

}
