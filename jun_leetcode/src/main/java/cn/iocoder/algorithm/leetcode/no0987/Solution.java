package cn.iocoder.algorithm.leetcode.no0987;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode-cn.com/problems/vertical-order-traversal-of-a-binary-tree/
 */
public class Solution {

    private class Node implements Comparable<Node> {

        /**
         * y 坐标
         */
        private Integer y;
        /**
         * 值
         */
        private Integer value;

        public Node(Integer y, Integer value) {
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            if (this.y.equals(o.y)) {
                return this.value - o.value;
            }
            return this.y - o.y;
        }
    }

    /**
     * 结果
     *
     * key ：x 坐标
     * value ：值 + y 坐标
     */
    private Map<Integer, List<Node>> results = new HashMap<>();
    /**
     * 最小 x 坐标
     */
    private int minX = 0;
    /**
     * 最大 x 坐标
     */
    private int maxX = 0;

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        this.dfs(root, 0, 0);

        // 组装结果
        List<List<Integer>> arrays = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            List<Node> array = results.get(i);
            if (array == null) {
                continue;
            }
            // 排序
            Collections.sort(array);
            arrays.add(array.stream().map(node -> node.value).collect(Collectors.toList()));
        }
        return arrays;
    }

    private void dfs(TreeNode node, int x, int y) {
        if (node == null) {
            return;
        }

        // 添加到其中
        List<Node> result = results.computeIfAbsent(x, k -> new ArrayList<>());
        result.add(new Node(y, node.val));

        // 设置新的
        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);

        // 左右节点
        this.dfs(node.left, x - 1, y + 1);
        this.dfs(node.right, x + 1, y + 1);
    }

    public static void main(String[] args) {
        TreeNode node = TreeNode.create(0,8,1,null,null,3,2,null,4,5,null,null,7,6);
        Solution solution = new Solution();
        System.out.println(solution.verticalTraversal(node));
    }

}
