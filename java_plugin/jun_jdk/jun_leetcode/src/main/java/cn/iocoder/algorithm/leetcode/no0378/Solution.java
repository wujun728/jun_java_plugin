package cn.iocoder.algorithm.leetcode.no0378;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/
 *
 * 堆排序
 */
public class Solution {

    /**
     * 队列元素
     */
    public class Item {

        public int value;
        public int row;
        public int col;

        public Item(int value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }

    }

    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0) {
            return -1;
        }

        // 创建一个优先队列，升序，并且值放 k 个元素
        Queue<Item> pq = new PriorityQueue<>(k, (o1, o2) -> o2.value - o1.value);
        int col = Math.min(k, matrix.length); // 每一行，需要遍历的个数。因为，只找 topK
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < col; j++) {
                //
                if (pq.size() < k) {
                    pq.add(new Item(matrix[i][j], i, j));
                    continue;
                }
                int max = pq.peek().value;
                if (matrix[i][j] < max) {
                    pq.remove();
                    pq.add(new Item(matrix[i][j], i, j));
                } else {
                    break;
                }
            }
        }

        return pq.peek().value;
    }

}
