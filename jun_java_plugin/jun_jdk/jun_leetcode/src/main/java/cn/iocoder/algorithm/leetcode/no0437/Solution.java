package cn.iocoder.algorithm.leetcode.no0437;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/path-sum-iii/
 */
public class Solution {

    private int pathCounts = 0;

    public int pathSum(TreeNode root, int sum) {
        this.traverseTreeNode(root, sum);

        return pathCounts;
    }

    public void traverseTreeNode(TreeNode root, int sum) {
        if (root == null) {
            return;
        }

        // 从该节点开始，寻找 sum
        this.doPathSum(root, sum);

        // 处理左右节点
        this.traverseTreeNode(root.left, sum);
        this.traverseTreeNode(root.right, sum);
    }

    public void doPathSum(TreeNode node, int sum) {
        // 排除当前节点为空的情况
        if (node == null) {
            return;
        }

        sum -= node.val;
        if (sum == 0) {
            pathCounts++;
        }

        // 处理左右节点
        this.doPathSum(node.left, sum);
        this.doPathSum(node.right, sum);
    }

}
