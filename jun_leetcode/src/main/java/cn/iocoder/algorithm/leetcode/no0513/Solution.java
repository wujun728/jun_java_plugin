package cn.iocoder.algorithm.leetcode.no0513;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/find-bottom-left-tree-value/
 */
public class Solution {

    public int findBottomLeftValue(TreeNode root) {
        assert root != null;

        // 初始
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode leftNode = null;

        // 遍历
        while (!queue.isEmpty()) {
            // 获得
            leftNode = queue.peek();

            // 遍历层级
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.poll();
                // 添加到 nodes 中
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        return leftNode.val;
    }

}
