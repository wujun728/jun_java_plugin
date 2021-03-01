package cn.iocoder.algorithm.leetcode.no0872;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/leaf-similar-trees/
 */
public class Solution {

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> results1 = new ArrayList<>();
        List<Integer> results2 = new ArrayList<>();
        // 收集叶子节点
        this.dfs(root1, results1);
        this.dfs(root2, results2);

        // 对比
        if (results1.size() != results2.size()) {
            return false;
        }
        for (int i = 0; i < results1.size(); i++) {
            if (!results1.get(i).equals(results2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void dfs(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            result.add(node.val);
        }

        // 左右节点
        this.dfs(node.left, result);
        this.dfs(node.right, result);
    }

}
