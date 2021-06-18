package cn.iocoder.algorithm.leetcode.no0266;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/
 */
public class Solution {

    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            // 交换
            TreeNode left = root.left;
            root.left = root.right;
            root.right = left;

            // 子节点
            this.invertTree(root.left);
            this.invertTree(root.right);
        }

        return root;
    }

}
