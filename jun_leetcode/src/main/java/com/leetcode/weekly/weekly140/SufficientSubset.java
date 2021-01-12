package com.leetcode.weekly.weekly140;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

/**
 * 根到叶路径上的不足节点
 *
 * @author: BaoZhou
 * @date : 2019/6/9 14:52
 */
public class SufficientSubset {
    @Test
    public void test() {
        TreeNode root = TreeWrapper.stringToTreeNode(" [1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14]");
        TreeWrapper.prettyPrintTree(sufficientSubset(root, 1));
    }

    public TreeNode sufficientSubset(TreeNode root, int limit) {
        int v = dfs5084(root, limit, 0);
        if (v < limit)
            return null;
        return root;
    }

    private int dfs5084(TreeNode node, int lim, int p) {
        if (node == null)
            return 0;
        if (node.left == null && node.right == null)
            return node.val;
        p += node.val;
        int sumLeft = 0, sumRight = 0;
        if (node.left != null) {
            sumLeft = dfs5084(node.left, lim, p);
            if (p + sumLeft < lim)
                node.left = null;
        }

        if (node.right != null) {
            sumRight = dfs5084(node.right, lim, p);
            if (p + sumRight < lim)
                node.right = null;
        }
        return node.val + Math.max(sumLeft, sumRight);
    }
}
