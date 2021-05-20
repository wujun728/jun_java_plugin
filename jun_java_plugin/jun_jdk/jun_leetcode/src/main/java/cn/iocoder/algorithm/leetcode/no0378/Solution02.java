package cn.iocoder.algorithm.leetcode.no0378;

import java.util.PriorityQueue;

/**
 * https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/
 *
 * 堆排序的优化，因为 {@link Solution} 并没有利用矩阵在垂直方向的升序的特性。
 *
 * 可参考博客：
 *  1. https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173 英文
 *  2. https://www.jianshu.com/p/873f6e7498e0 中文
 *
 * 解法确实巧妙，基本都是参考的第一篇博客。
 */
public class Solution02 {

    class Item implements Comparable<Item> {

        public int value;
        public int row;
        public int col;

        public Item(int row, int col, int value) {
            this.value = value;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Item that) { // 注意，这里是升序
            return this.value - that.value;
        }
    }

    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length, m = matrix[0].length;

        // 将第 0 行，按照升序添加到队列中
        PriorityQueue<Item> pq = new PriorityQueue<>(); // 小顶堆
        for(int j = 0; j < m; j++) {
            pq.offer(new Item(0, j, matrix[0][j]));
        }

        // 不断从小顶堆那出堆头，就是当前堆中的最小值，然后将其下一行添加到其中
        // 重复执行 k - 1 次，这样第 k 次拿到的堆头，就是第 k 大的值。
        // 这个推导过程，可以用笔画下。
        for(int i = 0; i < k - 1; i++) {
            // 拿出最小值
            Item item = pq.poll();
            // 已经到达最后一行，直接跳过
            if (item.row == n - 1) {
                continue;
            }
            // 添加下一行
            // 只能添加其下一行对应的元素的原因是，其他队列中的元素，下一行都比它自己大，所以除非它自己出，不然其下一行肯定不能进。
            pq.offer(new Item(item.row + 1, item.col, matrix[item.row + 1][item.col]));
        }

        return pq.poll().value;
    }



    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        if (false) {
            System.out.println(solution.kthSmallest(new int[][]{
                    {1, 5, 9},
                    {10, 11, 13},
                    {12, 13, 15}
            }, 8));
        }
        if (true) {
            System.out.println(solution.kthSmallest(new int[][]{
                    {1, 5, 9},
                    {2, 6, 10},
                    {8, 9, 11}
            }, 2));
        }
    }

}
