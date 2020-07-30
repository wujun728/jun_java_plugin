package cn.iocoder.algorithm.leetcode.no0111;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 *
 * 递归
 */
public class Solution {

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return this.minDepth(root, 0);
    }

    private int minDepth(TreeNode node, int level) {
        if (node.left == null && node.right == null) {
            return level + 1;
        }

        int leftDepth = node.left != null ? this.minDepth(node.left, level + 1)
                : Integer.MAX_VALUE;
        int rightDepth = node.right != null ? this.minDepth(node.right, level + 1)
                : Integer.MAX_VALUE;
        return Math.min(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode node = TreeNode.create(1, 2);
        System.out.println(solution.minDepth(node));
    }

}
