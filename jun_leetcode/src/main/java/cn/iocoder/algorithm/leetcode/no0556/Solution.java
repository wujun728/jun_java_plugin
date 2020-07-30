package cn.iocoder.algorithm.leetcode.no0556;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/sum-of-left-leaves/
 */
public class Solution {

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        // 添加左节点
        int sum = 0;
        if (root.left != null) {
            if (root.left.right == null && root.left.left == null) {
                sum += root.left.val;
            }
        }

        return sum
                + this.sumOfLeftLeaves(root.left)
                + this.sumOfLeftLeaves(root.right);
    }

    private void doSum(TreeNode node) {
        if (node == null) {
            return;
        }
    }

}
