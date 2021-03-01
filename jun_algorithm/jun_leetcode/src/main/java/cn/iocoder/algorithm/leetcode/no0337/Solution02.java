package cn.iocoder.algorithm.leetcode.no0337;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 动态规划
 *
 * 参考博客：https://leetcode-cn.com/problems/house-robber-iii/solution/java-2ms-by-horanol/
 */
public class Solution02 {

    public int rob(TreeNode root) {
        int[] results = this.doRob(root);
        return Math.max(results[0], results[1]);
    }

    private int[] doRob(TreeNode root) {
        // results[0] 包含当前节点 root 的最大值
        // results[1] 不包含当前节点 root 的最大值
        int[] results = new int[2];

        // 节点为空的情况
        if (root == null) {
            return results;
        }

        // 左子树，包含左节点和不包含左节点的最大值。
        int[] lefts = this.doRob(root.left);
        // 右子树，同上
        int[] rights = this.doRob(root.right);

        // 计算当前节点，包含和不包含的最大值。
        results[1] = Math.max(lefts[0], lefts[1]) + Math.max(rights[0], rights[1]);
        results[0] = root.val + lefts[1] + rights[1];
        return results;
    }

}
