package cn.iocoder.algorithm.leetcode.no0208;

/**
 * https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 */
public class Trie {

    private class TrieNode {

        /**
         * 子节点
         */
        private TrieNode[] children = new TrieNode[26];
        /**
         * 是否存在
         */
        private boolean leaf;

    }

    private TrieNode trie = new TrieNode();

    /** Initialize your data structure here. */
    public Trie() {}

    /** Inserts a word into the trie. */
    public void insert(String word) {
        // 先获得最终的 node
        TrieNode insertNode = trie;
        for (int i = 0; i < word.length(); i++) {
            TrieNode child = insertNode.children[word.charAt(i) - 'a'];
            if (child == null) {
                child = new TrieNode();
                insertNode.children[word.charAt(i) - 'a'] = child;
            }
            // 切换到下一个
            insertNode = child;
        }
        // 设置值
        insertNode.leaf = true;

    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode found = this.searchNode(word);
        return found != null && found.leaf;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return this.searchNode(prefix) != null;
    }

    private TrieNode searchNode(String word) {
        TrieNode prefixNode = trie;
        for (int i = 0; i < word.length(); i++) {
            prefixNode = prefixNode.children[word.charAt(i) - 'a'];
            if (prefixNode == null) {
                return null;
            }
        }
        return prefixNode;
    }

}
