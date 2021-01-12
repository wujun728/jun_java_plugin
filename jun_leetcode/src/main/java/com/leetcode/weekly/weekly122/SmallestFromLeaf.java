package com.leetcode.weekly.weekly122;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 从叶结点开始的最小字符串
 *
 * @author BaoZhou
 * @date 2019/1/27
 */
public class SmallestFromLeaf {

    @Test
    public void test() {
        TreeNode root = TreeWrapper.stringToTreeNode("[2,2,1,null,1,0,null,0]");
        smallestFromLeaf(root);
    }

    public String smallestFromLeaf(TreeNode root) {
        List<String> resultList = new ArrayList<>();
        dfs(root, new StringBuilder(), resultList);
        resultList.sort(String::compareTo);
        return resultList.get(0);
    }

    public void dfs(TreeNode node, StringBuilder s, List<String> result) {
        if (node.left == null && node.right == null) {
            s.append(backchar(node.val + 'a'));
            result.add(s.reverse().toString());
        }
        StringBuilder left = new StringBuilder(s);
        if (node.left != null) {
            dfs(node.left, left.append(backchar(node.val + 'a')), result);
        }
        StringBuilder right = new StringBuilder(s);
        if (node.right != null) {
            dfs(node.right, right.append(backchar(node.val + 'a')), result);
        }

    }

    public static char backchar(int backnum) {
        char strChar = (char) backnum;
        return strChar;
    }

}
