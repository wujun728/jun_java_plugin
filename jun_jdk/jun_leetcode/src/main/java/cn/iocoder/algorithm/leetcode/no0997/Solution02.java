package cn.iocoder.algorithm.leetcode.no0997;

/**
 * 寻找负数的结尾 left ，和非负数的开始 right。
 * 然后，left 向左，right 向右，不断比较，添加到结果。
 */
public class Solution02 {

    public int[] sortedSquares(int[] A) {
        // 寻找最后一个负数
        int left = -1;
        for (int i = 0; i < A.length; i++) {
            if (A[i] < 0) {
                left = i;
            } else {
                break;
            }
        }
        int right = left + 1;

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

}
