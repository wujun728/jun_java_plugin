package cn.iocoder.algorithm.leetcode.no0015;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基于双指针实现。
 *
 * 时间复杂度 O(N^2)
 *
 * LeetCode 结果 88ms
 */
public class Solution02 {

    public List<List<Integer>> threeSum(int[] nums) {
        // 排序
        Arrays.sort(nums);

        // 获得结果
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 排除，已经组合过一次的组合。相当于减枝，避免重复计算
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                // 相等
                if (sum == target) {
                    // 添加到结果
                    results.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 增大 left
                    do {
                        left++;
                    } while (nums[left] == nums[left - 1] && left < right);
                    // 减小 right
                    do {
                        right--;
                    } while (nums[right] == nums[right + 1] && left < right);
                    continue;
                }
                // 过大，需要进行缩小
                if (sum > target) {
                    // 减小 right
                    do {
                        right--;
                    } while (nums[right] == nums[right + 1] && left < right);
                    continue;
                }
                // 过小，需要进行扩大
                do {
                    left++;
                } while (nums[left] == nums[left - 1] && left < right);
            }
        }

        return results;
    }

}
