package cn.iocoder.algorithm.leetcode.no0814;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/binary-tree-pruning/
 *
 * 通过子节点求和
 */
public class Solution {

    public TreeNode pruneTree(TreeNode root) {
        TreeNode dummy = new TreeNode(1);
        dummy.left = root;
        this.pruneTree0(dummy);

        return dummy.left;
    }

    private int pruneTree0(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftSum = this.pruneTree0(node.left);
        if (leftSum == 0) {
            node.left = null;
        }
        int rightSum = this.pruneTree0(node.right);
        if (rightSum == 0) {
            node.right = null;
        }

        return leftSum + rightSum + node.val;
    }

}
