package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;


/**
 * 二叉搜索树与双向链表
 * 7
 * 4     9
 * 3   5   8  10
 *
 * @author BaoZhou
 * @date 2020-6-9
 */


public class Q26 {
    @Test
    public void result() {
        TreeNode head = TreeWrapper.stringToTreeNode("[]");
        Convert(head);
    }

    private TreeNode treeNode = null;

    public void ConvertDfs(TreeNode pRootOfTree) {
        if (pRootOfTree != null) {
            ConvertDfs(pRootOfTree.left);
            pRootOfTree.left = treeNode; //左节点指向小节点
            if (treeNode != null) {
                treeNode.right = pRootOfTree;//右节点指向大节点
            }
            treeNode = pRootOfTree; // 记录上一个遍历到的节点
            ConvertDfs(pRootOfTree.right);
        }
    }

    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree==null){
            return null;
        }
        ConvertDfs(pRootOfTree);
        while (pRootOfTree.left != null) {
            pRootOfTree = pRootOfTree.left;
        }
        return pRootOfTree;
    }
}
