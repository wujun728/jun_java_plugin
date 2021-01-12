package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

/**
 * 搜索旋转排序数组
 *
 * @author BaoZhou
 * @date 2019/5/5
 */
public class Search {
    @Test
    void test() {
        int[] nums = {5,1,3};
        System.out.println(search(nums, 1));
    }

    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int middle = (start + end) >> 1;
            if (nums[middle] == target) {
                return middle;
            }
            //头比中间大，说明旋转点在前半段，也就是后半段有序  【4 5 0 1 2 3】
            if (nums[start] > nums[middle]) {
                //如果目标大于中间小于头，说明所查找的数字在后半段
                if (target > nums[middle] && nums[start] > target) {
                    start = middle + 1;
                }
                //否在前半段
                else {
                    end = middle - 1;
                }
            }
            //否则，说明旋转点在后半段，也就是前半段要有序
            else {
                //如果目标小于中间大于头，说明所查找的数字在前半段
                if (target < nums[middle] && target >= nums[start]) {
                    end = middle - 1;
                }
                //否则在后半段
                else {
                    start = middle + 1;
                }
            }
        }
        return -1;
    }
}
