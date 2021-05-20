package cn.iocoder.algorithm.leetcode.no0016;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/3sum-closest/
 *
 * 双指针
 *
 * 参考博客：https://leetcode-cn.com/problems/3sum-closest/solution/hua-jie-suan-fa-16-zui-jie-jin-de-san-shu-zhi-he-b/
 */
public class Solution {

    public int threeSumClosest(int[] nums, int target) {
        assert nums.length >= 3;

        // 排序，因为要双指针遍历
        Arrays.sort(nums);

        int sumClosest = nums[0] + nums[1] + nums[2];
        int minDiff = Math.abs(target - sumClosest);
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过，相同的元素
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 计算当前目标
            int currentTarget = target - nums[i];
            // 双指针遍历
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int diff = currentTarget - nums[left] - nums[right];
                // 直接命中目标
                if (diff == 0) {
                    return target;
                }
                // 判断是否比当前更接近
                if (Math.abs(diff) < minDiff) {
                    minDiff = Math.abs(diff);
                    sumClosest = nums[i] + nums[left] + nums[right];
                }
                // 如果太小
                if (diff > 0) {
                    do {
                        left++;
                    } while (left < right && nums[left] == nums[left - 1]);
                    // 如果太大
                } else {
                    do {
                        right--;
                    } while (left < right && nums[right] == nums[right + 1]);
                }
            }
        }

        return sumClosest;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
    }

}
