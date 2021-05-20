package cn.iocoder.algorithm.leetcode.no0687;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * 写的时候，略微有点卡壳了。
 */
public class Solution02 {

    private int max = 0;

    public int longestUnivaluePath(TreeNode root) {
        this.doLongestUnivaluePath0(root);

        return max;
    }

    private int doLongestUnivaluePath0(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // 左节点的相同的编号的最大值
        int left = this.doLongestUnivaluePath0(root.left);
        // 右节点 + 同上
        int right = this.doLongestUnivaluePath0(root.right);

        // 如果当前节点和子节点相等，则使用 left + 1 ，否则只有 0
        left = root.left != null && root.left.val == root.val ? left + 1 : 0;
        // 右节点 + 同上
        right = root.right != null && root.right.val == root.val ? right + 1 : 0;

        // 求最大值
        max = Math.max(max, left + right);

        // 返回当前节点到达左子树，或者右子树，的最大值
        return Math.max(left, right);
    }

}
