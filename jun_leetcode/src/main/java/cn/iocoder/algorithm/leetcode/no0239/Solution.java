package cn.iocoder.algorithm.leetcode.no0239;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 *
 * 核心实现点，当 nodes 数组中，如果队尾的元素，小于新加入的数字，它就没有存在的意义
 */
public class Solution {

    private class Node {

        /**
         * 值
         */
        private int value;
        /**
         * 位置
         */
        private int index;

        public Node(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        List<Node> nodes = new ArrayList<>(k);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (!nodes.isEmpty()) {
                // 移除超过范围的
                if (i - nodes.get(0).index >= k) {
                    nodes.remove(0);
                }

                // 将队尾小于新加入的元素，全部移除
                while (!nodes.isEmpty()
                        && nodes.get(nodes.size() - 1).value < nums[i]) {
                    nodes.remove(nodes.size() - 1);
                }
            }

            // 添加到队列
            nodes.add(new Node(nums[i], i));

            // 添加到结果
            if (i >= k - 1) {
                result.add(nodes.get(0).value);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.maxSlidingWindow(new int[]{1, -1}, 1)));
    }

}
