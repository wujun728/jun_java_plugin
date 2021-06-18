package cn.iocoder.algorithm.leetcode.no0429;

import cn.iocoder.algorithm.leetcode.common.Node;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/n-ary-tree-level-order-traversal/
 *
 * 迭代
 */
public class Solution {

    public List<List<Integer>> levelOrder(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<List<Integer>> results = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int counts = queue.size();
            List<Integer> result = new ArrayList<>(counts);
            while (counts > 0) {
                counts--;
                // Node
                Node node = queue.poll();
                result.add(node.val);
                // 添加到队列
                if (node.children != null) {
                    queue.addAll(node.children);
                }
            }
            results.add(result);
        }

        return results;
    }

}
