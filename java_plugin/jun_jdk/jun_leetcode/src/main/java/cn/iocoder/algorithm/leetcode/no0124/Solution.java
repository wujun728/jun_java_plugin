package cn.iocoder.algorithm.leetcode.no0124;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/binary-tree-maximum-path-sum/
 */
public class Solution {

    private int max = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        this.sum(root);

        return max;
    }

    public int sum(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 左右节点的最大值
        int leftMax = Math.max(this.sum(node.left), 0);
        int rightMax = Math.max(this.sum(node.right), 0);

        // 计算当前节点 + 左右子树的最大值
        int sum = node.val + leftMax + rightMax;
        this.max = Math.max(max, sum);

        // 返回带上该节点的左或右节点最大值
        return node.val + Math.max(leftMax, rightMax);
    }

}
