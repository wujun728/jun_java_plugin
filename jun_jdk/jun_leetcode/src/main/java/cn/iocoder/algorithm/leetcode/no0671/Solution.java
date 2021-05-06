package cn.iocoder.algorithm.leetcode.no0671;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/second-minimum-node-in-a-binary-tree/
 *
 * 递归
 *
 * 不等于根节点的最小值，即第二大的值。
 */
public class Solution {

    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) {
            return -1;
        }
        if (root.left == null || root.right == null) { // 其实只要判断单边
            return -1;
        }

        // 寻找左右不等于根节点的最小值
        int leftValue = root.left.val == root.val ? this.findSecondMinimumValue(root.left)
                : root.left.val;
        int rightValue = root.right.val == root.val ? this.findSecondMinimumValue(root.right)
                : root.right.val;

        // 返回结果
        if (leftValue != -1 && rightValue != -1) {
            return Math.min(leftValue, rightValue);
        }
        return Math.max(leftValue, rightValue); // 有一个为 -1 ，所以可以直接求大
    }

}
