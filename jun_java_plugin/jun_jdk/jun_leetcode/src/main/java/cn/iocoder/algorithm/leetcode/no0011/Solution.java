package cn.iocoder.algorithm.leetcode.no0011;

/**
 * https://leetcode-cn.com/problems/container-with-most-water/
 *
 * 双指针
 *
 * 参考博客：https://leetcode-cn.com/problems/container-with-most-water/solution/container-with-most-water-shuang-zhi-zhen-fa-yi-do/
 */
public class Solution {

    public int maxArea(int[] height) {
        int max = 0;
        int left = 0, right = height.length - 1;
        while (left < right) {
            if (height[left] > height[right]) {
                max = Math.max(height[right] * (right - left), max);
                right--;
            } else {
                max = Math.max(height[left] * (right - left), max);
                left++;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

}
