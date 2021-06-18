package cn.iocoder.algorithm.leetcode.no0018;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/4sum/
 *
 * 双指针
 */
public class Solution {

    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) {
            return Collections.emptyList();
        }

        Arrays.sort(nums);

        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j - 1 != i && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 计算当前目标
                int currentTarget = target - nums[i] - nums[j];
                // 双指标遍历
                int left = j + 1, right = nums.length - 1;
                while (left < right) {
                    int sum = nums[left] + nums[right];
                    // 符合条件
                    if (sum == currentTarget) {
                        results.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        // 右移
                        do {
                            left++;
                        } while (left < right && nums[left] == nums[left - 1]);
                        // 左移
                        do {
                            right--;
                        } while (left < right && nums[right] == nums[right + 1]);
                        continue;
                    }
                    // 过小
                    if (sum < currentTarget) {
                        // 右移
                        do {
                            left++;
                        } while (left < right && nums[left] == nums[left - 1]);
                        continue;
                    }
                    // 过大
                    // 左移
                    do {
                        right--;
                    } while (left < right && nums[right] == nums[right + 1]);
                }
            }
        }

        return results;
    }

}
