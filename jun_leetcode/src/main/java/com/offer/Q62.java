package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

/**
 * 二叉搜索树的第k个结点
 * @author BaoZhou
 * @date 2020-6-26
 */
public class Q62 {

    @Test
    public void result() {
        TreeNode treeNode = TreeWrapper.stringToTreeNode("[5,3,7,2,4,6,8]");
        System.out.println(KthNode(treeNode, 3).val);
    }

    int count = 0;

    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null) {
            return null;
        }
        TreeNode[] result = new TreeNode[1];
        findNode(pRoot, k, result);
        return result[0];
    }

    void findNode(TreeNode node, int k, TreeNode[] result) {
        if (node.left != null) {
            findNode(node.left, k, result);
        }
        count++;
        if (count == k) {
            result[0] = node;
            return;
        }
        if (node.right != null) {
            findNode(node.right, k, result);
        }

    }
}
