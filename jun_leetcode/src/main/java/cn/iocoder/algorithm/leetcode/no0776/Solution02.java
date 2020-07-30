package cn.iocoder.algorithm.leetcode.no0776;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 在 {@link Solution} 上，简单优化，不每次 new 数组
 */
public class Solution02 {

    public TreeNode[] splitBST(TreeNode root, int V) {
        // 节点为空的情况
        if (root == null) {
            return new TreeNode[]{null, null};
        }

        if (V < root.val) {
            TreeNode[] results = this.splitBST(root.left, V);
            root.left = results[1];
            results[1] = root;
            return results;
        }
        TreeNode[] results = this.splitBST(root.right, V);
        root.right = results[0];
        results[0] = root;
        return results;
    }

}
