package cn.iocoder.algorithm.leetcode.no0701;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/insert-into-a-binary-search-tree/
 */
public class Solution {

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        this.dfs(root, val);

        return root;
    }

    private void dfs(TreeNode root, int val) {
        if (root.left == null && root.right == null) {
            TreeNode node = new TreeNode(val);
            if (val > root.val) {
                root.right = node;
            } else {
                root.left = node;
            }
            return;
        }

        if (val > root.val) {
            if (root.right == null) {
                root.right = new TreeNode(val);
            } else {
                this.dfs(root.right, val);
            }
        } else {
            if (root.left == null) {
                root.left = new TreeNode(val);
            } else {
                this.dfs(root.left, val);
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode root = TreeNode.create(4,2,7,1,3);
        solution.insertIntoBST(root, 5);
        System.out.println(root);
    }

}
