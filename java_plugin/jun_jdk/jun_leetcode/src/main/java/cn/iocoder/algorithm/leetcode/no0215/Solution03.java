package cn.iocoder.algorithm.leetcode.no0215;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 堆排序
 *
 * 使用优先级队列，实现小顶堆的效果
 */
public class Solution03 {

    public int findKthLargest(int[] nums, int k) {
        Queue<Integer> queue = new PriorityQueue<>(k);
        for (int num : nums) {
            if (queue.size() < k) {
                queue.add(num);
            } else {
                if (queue.peek() < num) {
                    queue.remove();
                    queue.add(num);
                }
            }
        }

        return queue.peek();
    }

}
