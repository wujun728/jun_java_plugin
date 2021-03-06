package com.jun.plugin.leetcode.algorithm.no0104;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 */
public class Solution {

    public int maxDepth(TreeNode root) {
        return dfs(root, 0);
    }

    public int dfs(TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }

        int leftDepth = dfs(node.left, depth + 1);
        int rightDepth = dfs(node.right, depth + 1);
        return Math.max(leftDepth, rightDepth);
    }

}
