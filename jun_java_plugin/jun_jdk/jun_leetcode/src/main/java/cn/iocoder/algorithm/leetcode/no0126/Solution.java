package cn.iocoder.algorithm.leetcode.no0126;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/word-ladder-ii/
 *
 * BFS 超时，显然不行。继续优化
 */
public class Solution {

    private class Node {

        private String lastWord;
        private LinkedHashSet<String> words;

        public Node(String word) {
            this.lastWord = word;
            this.words = new LinkedHashSet<>();
        }

        private Node add(String word) {
            Node node = new Node(word);
            node.lastWord = word;
            node.words = new LinkedHashSet<>(this.words);
            node.words.add(this.lastWord);
            return node;
        }

    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // 预先生成，每个字符串可匹配的结果
        Map<String, List<String>> normalizedMap = new HashMap<>();
        wordList.forEach(str -> {
            for (int i = 0; i < str.length(); i++) {
                String normalizedStr = this.normalized(str, i);
                List<String> array = normalizedMap.computeIfAbsent(normalizedStr, k -> new ArrayList<>());
                array.add(str);
            }
        });

        // 遍历
        List<List<String>> results = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(beginWord));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                for (int j = 0; j < node.lastWord.length(); j++) {
                    List<String> array = normalizedMap.get(this.normalized(node.lastWord, j));
                    if (array == null) {
                        continue;
                    }
                    for (String target : array) {
                        if (target.equals(endWord)) {
                            List<String> result = new ArrayList<>(node.words);
                            result.add(node.lastWord);
                            result.add(target);
                            results.add(result);
                            continue;
                        }
                        if (node.words.contains(target)) {
                            continue;
                        }
                        queue.add(node.add(target));
                    }
                }
            }
            if (!results.isEmpty()) {
                break;
            }
        }
        return results;
    }

    /**
     * 将字符串，指定位置修改成 *
     */
    private String normalized(String str, int index) {
        if (index == 0) {
            return new StringBuilder(str.length())
                    .append("*")
                    .append(str, index + 1, str.length())
                    .toString();
        }
        if (index == str.length() - 1) {
            return new StringBuilder(str.length())
                    .append(str, 0, index)
                    .append('*')
                    .toString();
        }
        return new StringBuilder(str.length())
                .append(str, 0, index)
                .append('*')
                .append(str, index + 1, str.length()).toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.findLadders("hit", "cog",
//                Arrays.asList("hot","dot","dog","lot","log","cog")));

        System.out.println(solution.findLadders("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log")));
    }

}
