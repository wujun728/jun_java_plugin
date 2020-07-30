package cn.iocoder.algorithm.leetcode.no0236;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 */
public class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null // 未找到
                || root == p || root == q) { // 找到 p 或 q
            return root;
        }

        // 遍历左右子树
        TreeNode left = this.lowestCommonAncestor(root.left, p, q);
        TreeNode right = this.lowestCommonAncestor(root.right, p, q);

        // 左子树找不到，说明在右子树
        if (left == null) {
            return right;
        }
        // 右子树找不到，说明在左子树
        if (right == null) {
            return left;
        }
        // 如果都不在，说明在其父亲的子树下
        return root;
    }

}
