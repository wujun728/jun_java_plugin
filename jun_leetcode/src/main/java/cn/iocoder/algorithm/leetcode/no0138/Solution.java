package cn.iocoder.algorithm.leetcode.no0138;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/copy-list-with-random-pointer/
 *
 * DFS
 *
 * TODO 芋艿，还有更多更优解 https://leetcode-cn.com/problems/copy-list-with-random-pointer/solution/fu-zhi-dai-sui-ji-zhi-zhen-de-lian-biao-by-leetcod/ 特别是方法三
 */
public class Solution {

    private Map<Node, Node> cloneNodes = new HashMap<>();

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // 获得缓存的节点
        Node cloneNode = cloneNodes.get(head);
        if (cloneNode != null) {
            return cloneNode;
        }
        // 创建克隆的节点
        cloneNode = new Node(head.val, null, null);
        cloneNodes.put(head, cloneNode);

        // 继续后续节点
        cloneNode.next = this.copyRandomList(head.next);
        cloneNode.random = this.copyRandomList(head.random);

        return cloneNode;
    }

}

class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};
