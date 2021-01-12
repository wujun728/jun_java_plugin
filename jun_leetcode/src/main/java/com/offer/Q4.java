package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 重建二叉树
 *
 * @author: BaoZhou
 * @date : 2020/5/28 9:57 上午
 */
public class Q4 {

    @Test
    public void result() {
        int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = {4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode result = reConstructBinaryTree(pre, in);
        TreeWrapper.prettyPrintTree(result);
    }


    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode result = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (in[i] == pre[0]) {
                result.left = reConstructBinaryTree(
                        Arrays.copyOfRange(pre, 1, i + 1),
                        Arrays.copyOfRange(in, 0, i));

                result.right = reConstructBinaryTree(
                        Arrays.copyOfRange(pre, i + 1, pre.length),
                        Arrays.copyOfRange(in, i + 1, pre.length));
                break;
            }

        }
        return result;
    }
}