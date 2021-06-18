package cn.iocoder.algorithm.leetcode.no0098;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 */
public class Solution {

    public boolean isValidBST(TreeNode root) {
        return this.isValidBST(root, null, null);
    }

    private boolean isValidBST(TreeNode node, Integer min, Integer max) {
        // 空的情况
        if (node == null) {
            return true;
        }

        // 判断节点是否有效
        if (min != null && min >= node.val) {
            return false;
        }
        if (max != null && max <= node.val) {
            return false;
        }

        // 左子树，最大值不能到达 node.val
        return this.isValidBST(node.left, min, node.val)
                // 右子树，最小值必须超过 node.val
                && this.isValidBST(node.right, node.val, max);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode node = TreeNode.create(10,5,15,null,null,6,20);
        System.out.println(solution.isValidBST(node));
    }

}
