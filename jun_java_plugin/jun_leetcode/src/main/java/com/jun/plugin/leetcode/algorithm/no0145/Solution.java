package com.jun.plugin.leetcode.algorithm.no0145;

import java.util.ArrayList;
import java.util.List;

import com.jun.plugin.leetcode.algorithm.common.TreeNode;

/**
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/
 *
 * 递归
 */
public class Solution {

    private List<Integer> results = new ArrayList<>();

    public List<Integer> postorderTraversal(TreeNode root) {
        this.recursion(root);

        return this.results;
    }

    public void recursion(TreeNode node) {
        if (node == null) {
            return;
        }

        // 左节点
        this.recursion(node.left);
        // 右节点
        this.recursion(node.right);

        // 当前节点
        this.visit(node);
    }

    private void visit(TreeNode node) {
        this.results.add(node.val);
    }

}
