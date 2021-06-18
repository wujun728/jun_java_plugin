package cn.iocoder.algorithm.leetcode.no0110;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/balanced-binary-tree/
 */
public class Solution {

    /**
     * 是否平衡
     */
    private boolean balanced = true;

    public boolean isBalanced(TreeNode root) {
        isBalanced(root, 0);

        return this.balanced;
    }

    private int isBalanced(TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }

        int leftDepth = isBalanced(node.left, depth + 1);
        if (balanced) { // 提前结束。其实，用处不大。
            return -1;
        }

        int rightDepth = isBalanced(node.right, depth + 1);
        if (balanced) { // 提前结束。其实，用处不大。
            return -1;
        }

        // 判断是否不平衡
        if (Math.abs(leftDepth - rightDepth) > 1) {
            balanced = false;
            return -1; // 提前结束
        }
        return Math.max(leftDepth, rightDepth);
    }

}
