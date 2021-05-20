package cn.iocoder.algorithm.leetcode.no0257;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-paths/
 */
public class Solution {

    private List<String> results = new ArrayList<>();

    public List<String> binaryTreePaths(TreeNode root) {
        this.dfs(root, "");

        return results;
    }

    private void dfs(TreeNode node, String str) {
        if (node == null) {
            return;
        }
        str += str.length() == 0 ? node.val : "->" + node.val;

        // 根节点，添加到结果
        if (node.left == null && node.right == null) {
            results.add(str);
            return;
        }

        this.dfs(node.left, str);
        this.dfs(node.right, str);
    }

}
