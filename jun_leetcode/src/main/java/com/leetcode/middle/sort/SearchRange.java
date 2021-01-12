package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 在排序数组中查找元素的第一个和最后一个位置
 *
 * @author: BaoZhou
 * @date : 2019/1/22 21:18
 */
public class SearchRange {
    @Test
    void test() {
        int[] nums = {2};
        System.out.println(Arrays.toString(searchRange(nums, 2)));
    }

    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        int pos = -1;
        int left = 0;
        int right = nums.length - 1;
        int mid = right / 2;
        while (left <= right) {
            if (nums[mid] == target) {
                pos = mid;
                break;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
            mid = (left + right) / 2;
        }
        if (pos == -1) {
            result[0] = -1;
            result[1] = -1;
        } else {
            int l = pos, r = pos;
            while (l >= 0 && nums[l] == target) {
                l--;
            }

            while (r < nums.length && nums[r] == target) {
                r++;
            }
            result[0] = l + 1;
            result[1] = r - 1;
        }
        return result;
    }


}
