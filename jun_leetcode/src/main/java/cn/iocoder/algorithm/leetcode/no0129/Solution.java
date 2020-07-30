package cn.iocoder.algorithm.leetcode.no0129;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/sum-root-to-leaf-numbers/
 */
public class Solution {

    public int sumNumbers(TreeNode root) {
        return this.sum(root, 0);
    }

    public int sum(TreeNode node, int baseVal) {
        if (node == null) {
            return 0;
        }

        baseVal = baseVal * 10 + node.val;
        if (node.left == null && node.right == null) {
            return baseVal;
        }
        return this.sum(node.left, baseVal)
                + this.sum(node.right, baseVal);
    }

}
