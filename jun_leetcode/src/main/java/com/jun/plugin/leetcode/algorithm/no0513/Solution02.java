package com.jun.plugin.leetcode.algorithm.no0513;

import java.util.LinkedList;
import java.util.Queue;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * 在 {@link Solution} 优化，先添加右节点，在添加左节点
 */
public class Solution02 {

    public int findBottomLeftValue(TreeNode root) {
        assert root != null;

        // 初始
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode leftNode = null;

        // 遍历
        while (!queue.isEmpty()) {
            // 获得
            leftNode = queue.poll();
            // 添加到 nodes 中
            if (leftNode.right != null) {
                queue.add(leftNode.right);
            }
            if (leftNode.left != null) {
                queue.add(leftNode.left);
            }
        }

        return leftNode.val;
    }

}
