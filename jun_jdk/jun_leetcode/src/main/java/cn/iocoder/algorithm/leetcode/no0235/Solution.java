package cn.iocoder.algorithm.leetcode.no0235;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 *
 * 递归
 */
public class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 比它们大，左子树
        if (root.val > p.val
            && root.val > q.val) {
            return this.lowestCommonAncestor(root.left, p, q);
        // 比它们小，右子树
        } else if (root.val < p.val
            && root.val < q.val) {
            return this.lowestCommonAncestor(root.right, p, q);
        // 之间，说明是最近祖先
        } else {
            return root;
        }
    }

}
