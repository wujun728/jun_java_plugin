package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;
import sun.plugin.javascript.navig.Array;

import java.util.Arrays;

/**
 * @author BaoZhou
 * @date 2019/1/8
 */
public class SortColors {
    @Test
    void test() {
        //int[] nums1 = {2, 0, 2, 1, 1, 0, 1, 1, 1, 1};
        //int[] nums2 = {2, 0, 1};
        int[] nums3 = {2, 0, 1};
        //sortColors(nums1);
        //sortColors(nums2);
        sortColors(nums3);
        //System.out.println(Arrays.toString(nums1));
        //System.out.println(Arrays.toString(nums2));
        System.out.println(Arrays.toString(nums3));
    }

    public void sortColors(int[] nums) {

        int start = 0;
        int left = 0;
        int right = nums.length - 1;
        int end = nums.length - 1;

        while (left < nums.length) {
            if (nums[left] == 0) {
                swap(nums, left, start++);
            }
            left++;
        }

        while (right >= start) {
            if (nums[right] == 2) {
                swap(nums, right, end--);
            }
            right--;
        }
    }


    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
