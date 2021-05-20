package cn.iocoder.algorithm.leetcode.no0987;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/vertical-order-traversal-of-a-binary-tree/
 *
 * 大体逻辑 ok 。未通过，后面改下。差在排序。
 */
public class Solution02 {

    /**
     * 结果
     *
     * key ：x 坐标
     * value ：值
     */
    private Map<Integer, List<Integer>> results = new HashMap<>();
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
        this.bfs(root);

        // 组装结果
        List<List<Integer>> arrays = new ArrayList<>();
        for (int i = minX; i <= maxX; i++) {
            List<Integer> array = results.get(i);
            if (array == null) {
                continue;
            }
            arrays.add(array);
        }
        return arrays;
    }

    private void bfs(TreeNode root) {
        if (root == null) {
            return;
        }

        // BFS
        Queue<TreeNode> queue = new LinkedList<>();
        Map<TreeNode, Integer> xMap = new HashMap<>(); // 坐标
        queue.add(root);
        xMap.put(root, 0);
        while (!queue.isEmpty()) {
            // 当前节点
            TreeNode node = queue.poll();
            Integer x = xMap.get(node);
            // 添加到其中
            List<Integer> result = results.get(x);
            if (result == null) {
                result = new ArrayList<>();
                result.add(node.val);
                results.put(x, result);
            } else {
                result.add(node.val);
            }
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);

            // 子节点
            if (node.left != null) {
                queue.add(node.left);
                xMap.put(node.left, x - 1);
            }
            if (node.right != null) {
                queue.add(node.right);
                xMap.put(node.right, x + 1);
            }
        }
    }

    public static void main(String[] args) {
        TreeNode node = TreeNode.create(0,8,1,null,null,3,2,null,4,5,null,null,7,6);
        Solution02 solution = new Solution02();
        System.out.println(solution.verticalTraversal(node));
    }

}
