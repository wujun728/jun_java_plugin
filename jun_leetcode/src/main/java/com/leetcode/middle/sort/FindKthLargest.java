package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 数组中的第K个最大元素
 *
 * @author BaoZhou
 * @date 2019/1/22
 */
public class FindKthLargest {
    @Test
    void test() {
        int[] nums = {6, 8, 1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println(findKthLargest(nums, k));
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int num : nums) {
            queue.add(num);
        }
        for (int i = 0; i < k - 1; i++) {
            queue.poll();
        }
        return queue.poll();
    }

    public int findKthLargest1(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }


}
