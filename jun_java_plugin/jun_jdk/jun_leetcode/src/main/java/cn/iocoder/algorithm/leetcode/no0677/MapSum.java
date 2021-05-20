package cn.iocoder.algorithm.leetcode.no0677;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/map-sum-pairs/
 */
public class MapSum {

    private class TrieNode {

        /**
         * 子节点
         */
        private TrieNode[] children = new TrieNode[26];
        /**
         * 值
         */
        private int value;

    }

    private TrieNode trie = new TrieNode();

    /** Initialize your data structure here. */
    public MapSum() {
    }

    public void insert(String key, int val) {
        // 先获得最终的 node
        TrieNode insertNode = trie;
        for (int i = 0; i < key.length(); i++) {
            TrieNode child = insertNode.children[key.charAt(i) - 'a'];
            if (child == null) {
                child = new TrieNode();
                insertNode.children[key.charAt(i) - 'a'] = child;
            }
            // 切换到下一个
            insertNode = child;
        }
        // 设置值
        insertNode.value = val;
    }

    public int sum(String prefix) {
        // 先获得最终的 node
        TrieNode prefixNode = trie;
        for (int i = 0; i < prefix.length(); i++) {
            prefixNode = prefixNode.children[prefix.charAt(i) - 'a'];
            if (prefixNode == null) {
                return 0;
            }
        }

        // 合计值
        return this.sumMethod02(prefixNode);
    }

    private int sumMethod01(TrieNode prefixNode) {
        int sum = 0;
        Queue<TrieNode> queue = new LinkedList<>();
        queue.add(prefixNode);
        while (!queue.isEmpty()) {
            TrieNode node = queue.poll();
            sum += node.value;
            // 子节点
            for (TrieNode child : node.children) {
                if (child != null) {
                    queue.add(child);
                }
            }
        }
        return sum;
    }

    private int sumMethod02(TrieNode prefixNode) {
        if (prefixNode == null) {
            return 0;
        }

        int sum = prefixNode.value;
        for (TrieNode child : prefixNode.children) {
            sum += this.sumMethod02(child);
        }
        return sum;
    }

    public static void main(String[] args) {
        MapSum mapSum = new MapSum();
        mapSum.insert("apple", 3);
        System.out.println(mapSum.sum("ap"));
        mapSum.insert("app", 2);
        System.out.println(mapSum.sum("ap"));
        System.out.println(mapSum.sum("appl"));
    }

}
