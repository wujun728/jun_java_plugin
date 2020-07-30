package cn.iocoder.algorithm.leetcode.no0162;

/**
 * 和 {@link Solution} 是的改良，参考 https://leetcode-cn.com/problems/find-peak-element/solution/xun-zhao-feng-zhi-by-leetcode/ 博客
 */
public class Solution03 {

    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if (middle == nums.length - 1
                || nums[middle] > nums[middle + 1]) { // 递减
//                left = middle + 1;
                right = middle - 1;
            } else { // 递增
//                right = middle - 1;
                left = middle + 1;
            }
        }

        // 不断缩减搜寻范围，最终就是 left
        return left;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
//        System.out.println(solution.findPeakElement(new int[]{1,2,3,1}));
//        System.out.println(solution.findPeakElement(new int[]{1,2,1,3,5,6,4}));
//        System.out.println(solution.findPeakElement(new int[]{1}));
        System.out.println(solution.findPeakElement(new int[]{1, 3, 1}));
        System.out.println(solution.findPeakElement(new int[]{1, 3}));
        System.out.println(solution.findPeakElement(new int[]{3, 1}));
    }

}
