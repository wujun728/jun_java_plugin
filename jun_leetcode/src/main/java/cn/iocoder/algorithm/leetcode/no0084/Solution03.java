package cn.iocoder.algorithm.leetcode.no0084;

/**
 * 分治算法
 *
 * 将求整个区间内的最大面积，分治成多个区间的最大面积。
 *
 * 平均时间复杂度，O(nlogn)
 * 最差时间复杂度，O(n^2)
 *
 * 有个方案优化，稳定在 O(nlogn) ，具体是 https://leetcode-cn.com/problems/largest-rectangle-in-histogram/solution/zhu-zhuang-tu-zhong-zui-da-de-ju-xing-by-leetcode/ 方法四。
 */
public class Solution03 {

    private int partition(int[] heights, int start, int end) {
        if (start > end) {
            return 0;
        }
        int minIndex = start;
        for (int i = start + 1; i <= end; i++) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i;
            }
        }
        // 当前面积
        int area = heights[minIndex] * (end - start + 1);
        // 左右面积
        int left = this.partition(heights, start, minIndex - 1);
        int right = this.partition(heights, minIndex + 1, end);
        return Math.max(area, Math.max(left, right));
    }

    public int largestRectangleArea(int[] heights) {
        return this.partition(heights, 0, heights.length - 1);
    }

}
