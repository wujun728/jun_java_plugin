package com.jun.plugin.leetcode.algorithm.no0589;

import java.util.ArrayList;
import java.util.List;

import com.jun.plugin.leetcode.algorithm.common.Node;

/**
 * https://leetcode-cn.com/problems/n-ary-tree-preorder-traversal/
 *
 * 递归
 */
public class Solution {

    private List<Integer> results = new ArrayList<>();

    public List<Integer> preorder(Node root) {
        this.traversal(root);

        return results;
    }

    private void traversal(Node node) {
        if (node == null) {
            return;
        }

        // 当前节点
        results.add(node.val);

        // 子节点
        if (node.children != null) {
            for (Node child : node.children) {
                this.traversal(child);
            }
        }
    }

}
