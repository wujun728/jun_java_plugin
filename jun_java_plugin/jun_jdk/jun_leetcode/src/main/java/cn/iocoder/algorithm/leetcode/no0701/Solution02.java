package cn.iocoder.algorithm.leetcode.no0701;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 和 {@link Solution} 一样的思路，差别在于简化代码
 */
public class Solution02 {

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val > root.val) {
            root.right = this.insertIntoBST(root.right, val);
        } else {
            root.left = this.insertIntoBST(root.left, val);
        }

        return root;
    }

}
