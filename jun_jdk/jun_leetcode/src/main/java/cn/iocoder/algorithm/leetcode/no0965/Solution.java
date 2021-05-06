package cn.iocoder.algorithm.leetcode.no0965;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/univalued-binary-tree/
 *
 * 递归
 */
public class Solution {

    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSame(root, root.val);
    }

    private boolean isSame(TreeNode node, int val) {
        if (node == null) {
            return true;
        }
        if (node.val != val) {
            return false;
        }

        return this.isSame(node.left, val)
                && this.isSame(node.right, val);
    }

}
