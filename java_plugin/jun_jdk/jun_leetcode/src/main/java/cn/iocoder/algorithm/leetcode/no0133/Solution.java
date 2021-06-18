package cn.iocoder.algorithm.leetcode.no0133;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/clone-graph/
 *
 * DFS 递归实现。
 *
 * 当然，也可以使用 BFS 实现。
 */
public class Solution {

    private Map<Node, Node> cloneNodes = new HashMap<>();

    public Node cloneGraph(Node node) {
        // 判断是否访问过
        Node cloneNode = cloneNodes.get(node);
        if (cloneNode != null) {
            return cloneNode;
        }
        // 创建克隆的 node
        cloneNode = new Node(node.val, new ArrayList<>());
        cloneNodes.put(node, cloneNode);

        // 遍历节点
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(this.cloneGraph(neighbor));
        }
        return cloneNode;
    }

}
