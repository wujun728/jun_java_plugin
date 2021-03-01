package cn.iocoder.algorithm.leetcode.no0617;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/merge-two-binary-trees/
 */
public class Solution {

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        // 如果 t1 为空，则返回 t2
        if (t1 == null) {
            return t2;
        }

        // 合并节点
        if (t2 != null) {
            t1.val += t2.val;
            t1.left = this.mergeTrees(t1.left, t2.left);
            t1.right = this.mergeTrees(t1.right, t2.right);
        }

        return t1;
    }

}
