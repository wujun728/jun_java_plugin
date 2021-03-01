package cn.iocoder.algorithm.leetcode.no0437;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 在 {@link Solution} 的方案上，进一步简化
 */
public class Solution02 {

    private int pathCounts = 0;

    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        return this.doPathSum(root, sum)
                + this.pathSum(root.left, sum)
                + this.pathSum(root.right, sum);
    }

    public int doPathSum(TreeNode node, int sum) {
        // 排除当前节点为空的情况
        if (node == null) {
            return 0;
        }

        sum -= node.val;

        // 处理左右节点
        return (sum == 0 ? 1 : 0)
                + this.doPathSum(node.left, sum)
                +  this.doPathSum(node.right, sum);
    }

}
