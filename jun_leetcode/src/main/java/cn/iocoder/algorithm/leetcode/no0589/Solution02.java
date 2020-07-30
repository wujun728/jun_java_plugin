package cn.iocoder.algorithm.leetcode.no0589;

import cn.iocoder.algorithm.leetcode.common.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 迭代
 */
public class Solution02 {

    public List<Integer> preorder(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<Integer> results = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            // 添加到 results 中
            Node node = stack.pop();
            results.add(node.val);
            // 倒序添加到 stack 中
            if (node.children != null) {
                for (int i = node.children.size() - 1; i >= 0; i--) {
                    stack.push(node.children.get(i));

                }
            }
        }

        return results;
    }

}
