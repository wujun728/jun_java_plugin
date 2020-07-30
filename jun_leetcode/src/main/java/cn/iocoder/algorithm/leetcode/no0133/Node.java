package cn.iocoder.algorithm.leetcode.no0133;

import java.util.List;

class Node {

    public int val;
    public List<Node> neighbors;

    public Node() {
    }

    public Node(int _val, List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }

}
