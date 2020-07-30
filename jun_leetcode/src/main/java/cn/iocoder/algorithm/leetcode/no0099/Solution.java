package cn.iocoder.algorithm.leetcode.no0099;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/recover-binary-search-tree/
 */
public class Solution {

    private TreeNode lastNode;
    private List<TreeNode> results = new ArrayList<>(4);

    public void recoverTree(TreeNode root) {
        this.dfs(root);

        // 交换修复
        TreeNode first = results.get(0);
        TreeNode second = results.get(results.size() - 1);
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        // 左子树
        this.dfs(node.left);

        // 查到不匹配的
        if (lastNode != null && node.val < lastNode.val) {
            results.add(lastNode);
            results.add(node);
        }
        // 因为最多只有两个节点交换，所以到达 4 个，就 break
        if (results.size() == 4) {
            return;
        }
        // 记录最后节点
        this.lastNode = node;

        // 右子树
        this.dfs(node.right);
    }

}
