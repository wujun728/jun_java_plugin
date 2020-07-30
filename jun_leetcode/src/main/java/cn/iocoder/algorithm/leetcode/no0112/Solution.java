package cn.iocoder.algorithm.leetcode.no0112;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/path-sum/
 *
 * 递归
 */
public class Solution {

//    public boolean hasPathSum(TreeNode root, int sum) {
//        // 处理为空的情况
//        if (root == null) {
//            return false;
//        }
//
//        // 处理是否为叶子节点
//        if (root.left == null && root.right == null) {
//            return root.val == sum;
//        }
//
//        // 左节点
//        if (root.left != null && hasPathSum(root.left, sum - root.val)) {
//            return true;
//        }
//        // 右节点
//        if (root.right != null && hasPathSum(root.right, sum - root.val)) {
//            return true;
//        }
//
//        return false;
//    }

    // 进一步简化
    public boolean hasPathSum(TreeNode root, int sum) {
        // 处理为空的情况
        if (root == null) {
            return false;
        }

        sum -= root.val;

        // 处理是否为叶子节点
        if (root.left == null && root.right == null) {
            return sum == 0;
        }

        return hasPathSum(root.left, sum)
                || hasPathSum(root.right, sum);
    }

}
