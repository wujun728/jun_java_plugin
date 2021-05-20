package cn.iocoder.algorithm.leetcode.no0145;

import cn.iocoder.algorithm.leetcode.common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 迭代
 *
 * 左右中 = 中右左的逆转
 */
public class Solution02 {

    public List<Integer> postorderTraversal(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> results = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            // 当前节点
            TreeNode node = stack.pop();
            results.add(node.val);
            // 左右节点。虽然遍历是中右左，但是 stack 是先进后出，所以添加是左右的顺序
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }

        Collections.reverse(results);
        return results;
    }

}
