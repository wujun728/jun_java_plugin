package cn.iocoder.algorithm.leetcode.no0239;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 {@link Solution} 的基础上，进一步优化。
 *
 * 主要是，如果新加入的元素，大于所有的元素，直接清空队列
 */
public class Solution02 {

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
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= max) {
                nodes.clear();
            } else {
                // 移除超过范围的
                if (!nodes.isEmpty() && i - nodes.get(0).index >= k) {
                    nodes.remove(0);
                }

                // 将队尾小于新加入的元素，全部移除
                while (!nodes.isEmpty()
                        && nodes.get(nodes.size() - 1).value <= nums[i]) {
                    nodes.remove(nodes.size() - 1);
                }
            }

            // 添加到队列
            nodes.add(new Node(nums[i], i));

            // 添加到结果
            max = nodes.get(0).value;
            if (i >= k - 1) {
                result.add(nodes.get(0).value);
            }
        }

        return result.stream().mapToInt(i -> i).toArray();
    }

}
