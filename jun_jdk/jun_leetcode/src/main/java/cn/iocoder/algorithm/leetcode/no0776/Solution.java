package cn.iocoder.algorithm.leetcode.no0776;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/split-bst/
 *
 * 递归
 */
public class Solution {

    // results[0] 小于等于 V 的子树
    // results[1] 大于 V 的子树
    public TreeNode[] splitBST(TreeNode root, int V) {
        // 节点为空的情况
        if (root == null) {
            return new TreeNode[]{null, null};
        }

        // 值比当前节点小，则递归左子树
        if (V < root.val) {
            // 递归，按照 V 拆分左右子树
            TreeNode[] results = this.splitBST(root.left, V);
            // 将返回的右子树，作为左节点。因为，返回的是比 V 大的右子树
            root.left = results[1];
            // 返回结果
            return new TreeNode[]{results[0], root};
        } else {
            // 递归，按照 V 拆分左右子树
            TreeNode[] results = this.splitBST(root.right, V);
            // 将返回的左子树，作为右节点。因为，返回的是比 V 小的左子树
            root.right = results[0];
            // 返回结果
            return new TreeNode[]{root, results[1]};
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode node = TreeNode.create(4,2,6,1,3,5,7);
        System.out.println(Arrays.toString(solution.splitBST(node, 2)));
    }

}
