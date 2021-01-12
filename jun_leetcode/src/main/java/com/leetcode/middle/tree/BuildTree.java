package com.leetcode.middle.tree;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;

/**
 * 从前序与中序遍历序列构造二叉树
 *
 * @author BaoZhou
 * @date 2018/12/29
 */
public class BuildTree {
    public static void main(String[] args) {
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};
        TreeNode node = buildTree(preorder, inorder);
        TreeWrapper.prettyPrintTree(node);
    }


    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }

        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        TreeNode mid = new TreeNode(preorder[preStart]);
        int pos = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (preorder[preStart] == inorder[i]) {
                pos = i;
                break;
            }
        }
        //左树的元素个数
        int leftCount = pos - inStart;
        mid.left = buildTree(preorder, inorder, preStart + 1, preStart + leftCount, inStart, pos - 1);
        mid.right = buildTree(preorder, inorder, preStart + leftCount + 1, preEnd, pos + 1, inEnd);
        return mid;
    }
}
