package com.offer;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;


/**
 * 两个链表的第一个公共结点
 *            1
 *          2  3
 *       4   5   6
 *         7
 * @author BaoZhou
 * @date 2020-6-16
 */

public class Q38 {
    @Test
    public void result() {
//        TreeNode root = TreeWrapper.stringToTreeNode("[1,2,3,4,5,null,6,null,null,7]");
        TreeNode root = TreeWrapper.stringToTreeNode("[1]");
        System.out.println(TreeDepth(root));
    }


    int maxDepth;

    public int TreeDepth(TreeNode root) {
        if(root == null)
        return  0;

        maxDepth = 0;
        dfs(1,root);
        return maxDepth;
    }

    private void dfs(int depth, TreeNode node) {
        if (depth > maxDepth) maxDepth = depth;
        if (node.left != null) {
            dfs(depth + 1, node.left);
        }
        if (node.right != null) {
            dfs(depth + 1, node.right);
        }
    }

}



