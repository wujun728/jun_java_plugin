package cn.iocoder.algorithm.leetcode.no0530;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst/
 */
public class Solution {

    private Integer lastValue;
    private int min = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {
        this.dfs(root);

        return min;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }

        // 左节点
        this.dfs(node.left);

        // 当前节点
        if (this.lastValue != null) {
            this.min = Math.min(node.val - this.lastValue, min);
        }
        this.lastValue = node.val;


        // 右节点
        this.dfs(node.right);
    }

}
