package cn.iocoder.algorithm.leetcode.no0814;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 在 {@link Solution} 上，优化递归逻辑
 */
public class Solution02 {

    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 递归左右子树
        root.left = this.pruneTree(root.left);
        root.right = this.pruneTree(root.right);

        if (root.val == 0 && root.left == null && root.right == null) {
            return null;
        }
        return root;
    }

}
