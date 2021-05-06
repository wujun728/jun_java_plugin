package cn.iocoder.algorithm.leetcode.no0337;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/house-robber-iii/
 *
 * 暴力 DFS 递归
 *
 * 因为存在重复访问，所以效率低
 */
public class Solution {

    public int rob(TreeNode root) {
        // root 为空的情况
        if (root == null) {
            return 0;
        }

        // 计算使用当前节点，和排序其子节点
        int value1 = root.val;
        if (root.left != null) {
            value1 += this.rob(root.left.left) + this.rob(root.left.right);
        }
        if (root.right != null) {
            value1 += this.rob(root.right.left) + this.rob(root.right.right);
        }

        // 不使用当前节点
        int value2 = this.rob(root.left) + this.rob(root.right);

        return Math.max(value1, value2);
    }

}
