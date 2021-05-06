package cn.iocoder.algorithm.leetcode.no0235;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 迭代
 */
public class Solution02 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            if (root.val > p.val && root.val > q.val) { // 比它们大，左子树
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) { // 比它们小，右子树
                root = root.right;
            // 之间，说明是最近祖先
            } else {
                return root;
            }
        }
        return null;
    }

}
