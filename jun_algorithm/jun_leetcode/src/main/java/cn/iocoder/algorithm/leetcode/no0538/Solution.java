package cn.iocoder.algorithm.leetcode.no0538;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
 */
public class Solution {

    private int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root == null) {
            return null;
        }

        // 右节点
        this.convertBST(root.right);

        // 当前节点
        root.val += sum;
        sum += root.val - sum;

        // 左节点
        this.convertBST(root.left);

        return root;
    }

}
