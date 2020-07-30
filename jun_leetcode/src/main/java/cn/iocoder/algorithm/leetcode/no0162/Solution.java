package cn.iocoder.algorithm.leetcode.no0162;

/**
 * https://leetcode-cn.com/problems/find-peak-element/
 *
 * 二分查找的变种
 *
 * nums 数组，可以理解成交替的升序和降序的子数组。
 *
 * 因为，nums[-1] = nums[n] = -无穷，所以一定有解
 */
public class Solution {

    public int findPeakElement(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            if ((middle == 0 || nums[middle - 1] < nums[middle])
                    && (middle == nums.length - 1 || nums[middle] > nums[middle + 1])) {
                return middle;
            }
//            System.out.println(left + "\t" + right + "\t" + middle);
            // 如下可以简化判断的原因是，上面已经判断是否在交界处。
            if (middle == 0 || nums[middle - 1] < nums[middle]) { // 递增区域
                left = middle + 1;
            } else { // 递减区域
                right = middle - 1;
            }
        }

        throw new IllegalArgumentException("根据题目，不存在这个情况");
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.findPeakElement(new int[]{1,2,3,1}));
//        System.out.println(solution.findPeakElement(new int[]{1,2,1,3,5,6,4}));
//        System.out.println(solution.findPeakElement(new int[]{1}));
        System.out.println(solution.findPeakElement(new int[]{1, 3, 1}));
        System.out.println(solution.findPeakElement(new int[]{1, 3}));
        System.out.println(solution.findPeakElement(new int[]{3, 1}));
    }

}
