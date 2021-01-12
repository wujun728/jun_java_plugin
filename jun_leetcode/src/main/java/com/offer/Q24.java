package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Stack;
/**
 * 二叉搜索树的后序遍历序列
 *
 * @author BaoZhou
 * @date 2020-6-9
 */

public class Q24 {


    @Test
    public void result() {
        TreeNode head = TreeWrapper.stringToTreeNode("[1,2,3,4,5,-3,-3,3,9]");

        System.out.println(FindPath(head, 1).toString());
    }

    private ArrayList<ArrayList<Integer>> result = new ArrayList<>();

    private Stack<Integer> path = new Stack<>();


    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        if (root == null) return result;
        path.add(root.val);
        if (target == root.val && root.left == null && root.right == null) {
            //这里要new一个list，才能在堆中创建一个新的数组。path是在常量池中，每个方法栈共享，最后会导致结果出错
            result.add(new ArrayList<>(path));
        }
        if (root.left != null) {
            FindPath(root.left, target - root.val);
            path.remove(path.size() - 1);
        }
        if (root.right != null) {
            FindPath(root.right, target - root.val);
            path.remove(path.size() - 1);
        }
        return result;
    }
}
