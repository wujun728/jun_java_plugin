package cn.iocoder.algorithm.leetcode.no0215;

import java.util.Random;

/**
 * 在 {@link Solution021} 的基础上，继续优化。
 * <p>
 * 参考 https://leetcode-cn.com/problems/kth-largest-element-in-an-array/solution/shu-zu-zhong-de-di-kge-zui-da-yuan-su-by-leetcode/ 博客，方法二：快速选择。
 */
public class Solution022 {

    private int[] nums;

    public int findKthLargest(int[] nums, int k) {
        this.nums = nums;
        int size = nums.length;
        // kth largest is (N - k)th smallest
        return quickSelect(0, size - 1, size - k);
    }

    // 返回结果，是具体数值
    public int quickSelect(int left, int right, int k_smallest) {
        // Returns the k-th smallest element of list within left..right.
        if (left == right) { // If the list contains only one element,
            return this.nums[left];  // return that element
        }

        //【重点差距】select a random pivot_index 随机一个分区位
        Random random_num = new Random();
        int pivot_index = left + random_num.nextInt(right - left);
        //【重点差距】执行分区，获得最终的分区位
        pivot_index = partition(left, right, pivot_index);

        // the pivot is on (N - k)th smallest position
        if (k_smallest == pivot_index) {
            return this.nums[k_smallest];
        }
        // go left side
        if (k_smallest < pivot_index) {
            return quickSelect(left, pivot_index - 1, k_smallest);
        }
        // go right side
        return quickSelect(pivot_index + 1, right, k_smallest);
    }

    public int partition(int left, int right, int pivot_index) {
        int pivot = this.nums[pivot_index];
        // 1. move pivot to end
        swap(pivot_index, right);
        int store_index = left;

        // 2. move all smaller elements to the left
        for (int i = left; i <= right; i++) {
            if (this.nums[i] < pivot) {
                swap(store_index, i);
                store_index++;
            }
        }

        // 3. move pivot to its final place
        swap(store_index, right);

        return store_index;
    }

    public void swap(int a, int b) {
        int tmp = this.nums[a];
        this.nums[a] = this.nums[b];
        this.nums[b] = tmp;
    }

}
