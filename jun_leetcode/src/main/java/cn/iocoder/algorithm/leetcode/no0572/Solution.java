package cn.iocoder.algorithm.leetcode.no0572;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/subtree-of-another-tree/
 */
public class Solution {

    public boolean isSubtree(TreeNode s, TreeNode t) {
        // 如果为空，结束
        if (s == null) {
            return false;
        }

        // 当前节点
        if (this.isSubTree0(s, t)) {
            return true;
        }

        if (isSubtree(s.left, t)
            || isSubtree(s.right, t)) {
            return true;
        }

        return false;
    }

    private boolean isSubTree0(TreeNode s, TreeNode t) {
        // 如果 s 为空
        if (s == null) {
            return t == null;
        }

        // 如果 t 为空，则不匹配
        if (t == null) {
            return false;
        }

        // 当前节点的值的比较
        if (s.val != t.val) {
            return false;
        }

        // 继续子节点
        return this.isSubTree0(s.left, t.left)
                && this.isSubTree0(s.right, t.right);
    }

    public static void main(String[] args) {
        TreeNode s = TreeNode.create(3, 4, 5, 1, 2, null, null, 0);
        TreeNode t = TreeNode.create(4, 1, 2);
        Solution solution = new Solution();
        System.out.println(solution.isSubtree(s, t));
    }

}
