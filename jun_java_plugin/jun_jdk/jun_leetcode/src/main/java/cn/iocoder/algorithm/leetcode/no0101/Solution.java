package cn.iocoder.algorithm.leetcode.no0101;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/symmetric-tree/
 */
public class Solution {

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return this.isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }

        return isMirror(left.right, right.left)
                && isMirror(left.left, right.right);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode node = TreeNode.create(1, 2, 2, 3, 4, 4, 3);
        System.out.println(solution.isSymmetric(node));
        node = TreeNode.create(1, 2, 2, null, 3, null, 3);
        System.out.println(solution.isSymmetric(node));
    }

}
