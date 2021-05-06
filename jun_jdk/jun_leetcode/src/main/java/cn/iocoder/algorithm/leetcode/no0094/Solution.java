package cn.iocoder.algorithm.leetcode.no0094;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 *
 * 递归
 */
public class Solution {

    private List<Integer> results = new ArrayList<>();

    public List<Integer> inorderTraversal(TreeNode root) {
        this.recursion(root);

        return results;
    }

    public void recursion(TreeNode node) {
        if (node == null) {
            return;
        }

        // 左节点
        this.recursion(node.left);

        // 当前节点
        this.visit(node);

        // 右节点
        this.recursion(node.right);
    }

    private void visit(TreeNode node) {
        this.results.add(node.val);
    }

}
