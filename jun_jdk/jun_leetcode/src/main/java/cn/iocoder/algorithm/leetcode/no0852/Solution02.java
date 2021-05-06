package cn.iocoder.algorithm.leetcode.no0852;

/**
 * 二分查找
 */
public class Solution02 {

    public int peakIndexInMountainArray(int[] A) {
        assert A.length >= 3;

        int left = 0, right = A.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (A[middle - 1] < A[middle] && A[middle] > A[middle + 1]) {
                return middle;
            }
//            System.out.println(left + "\t" + right + "\t" + middle);
            // 如下可以简化判断的原因是，上面已经判断是否在交界处。
            if (A[middle - 1] < A[middle]) { // 递增区域
                left = middle + 1;
            } else { // 递减区域
                right = middle - 1;
            }
        }

        throw new IllegalArgumentException("根据题目，不存在这个情况");
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.peakIndexInMountainArray(new int[]{0, 1, 0}));
//        System.out.println(solution.peakIndexInMountainArray(new int[]{0, 2, 1, 0}));
        System.out.println(solution.peakIndexInMountainArray(new int[]{24, 69, 100, 99, 79, 78, 67, 36, 26, 19}));
    }

}
