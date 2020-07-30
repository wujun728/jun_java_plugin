package cn.iocoder.algorithm.leetcode.no0084;

/**
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 *
 * 每个节点，如果左（右）节点比自己打，则进行遍历。
 * 如此，我们就能求出，以当前节点为高度，最大能到达多远，从而计算出宽度 = right - left + 1 。
 * 然后求面积，求最大值。
 */
public class Solution {

    public int largestRectangleArea(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            // left 下标
            int left = i;
            while (left > 0 && heights[left - 1] >= heights[i]) {
                left--;
            }
            // right 下标
            int right = i;
            while (right < heights.length - 1 && heights[right + 1] >= heights[i]) {
                right++;
            }
            // 求 max
            max = Math.max(max, (right - left + 1) * heights[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }

}
