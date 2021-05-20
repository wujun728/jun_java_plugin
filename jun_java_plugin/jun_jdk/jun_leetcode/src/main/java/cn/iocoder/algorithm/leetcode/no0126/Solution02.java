package cn.iocoder.algorithm.leetcode.no0126;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/word-ladder-ii/
 *
 * BFS
 */
public class Solution02 {

    private class Node {

        private String word;
        private List<Node> prevs;

        public Node(String word) {
            this.word = word;
            this.prevs = null;
        }

        public Node(String word, Node prev) {
            List<Node> prevs = new ArrayList<>(1);
            prevs.add(prev);
            this.word = word;
            this.prevs = prevs;
        }

        private void add(Node node) {
            this.prevs.add(node);
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
        List<Node> results = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(beginWord));
        Set<String> visited = new HashSet<>(); // 截止至上一轮，已经访问过的节点
        visited.add(beginWord);
        while (!queue.isEmpty()) {
            Map<String, Node> currentNodes = new HashMap<>(); // 当前轮，新产生的访问节点
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                for (int j = 0; j < node.word.length(); j++) {
                    List<String> array = normalizedMap.get(this.normalized(node.word, j));
                    if (array == null) {
                        continue;
                    }
                    for (String target : array) {
                        // 已经找到，添加到 results 结果
                        if (target.equals(endWord)) {
                            results.add(new Node(target, node));
                            continue;
                        }
                        // 上一轮已经访问过，跳过
                        if (visited.contains(target)) {
                            continue;
                        }
                        // 本轮刚访问过，添加到已访问的节点上
                        Node currentVisitNode = currentNodes.get(target);
                        if (currentVisitNode != null) {
                            currentVisitNode.add(node);
                            continue;
                        }
                        // 本轮未访问过，创建该新节点
                        currentVisitNode = new Node(target, node);
                        currentNodes.put(target, currentVisitNode);
                        queue.add(currentVisitNode);
                    }
                }
            }
            // 已经找到，结束
            if (!results.isEmpty()) {
                break;
            }
            // 将本轮访问过的，添加到 visited 中
            visited.addAll(currentNodes.keySet());
        }

        // 转换返回
        return  this.genResult(results);
    }

    private List<List<String>> genResult(List<Node> nodes) {
        List<List<String>> results = new ArrayList<>();
        if (nodes == null) {
            results.add(new ArrayList<>());
            return results;
        }
        for (Node node : nodes) {
            for (List<String> result : this.genResult(node.prevs)) {
                result.add(node.word);
                results.add(result);
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
        Solution02 solution = new Solution02();
//        System.out.println(solution.findLadders("hit", "cog",
//                Arrays.asList("hot","dot","dog","lot","log","cog")));

//        System.out.println(solution.findLadders("hit", "cog",
//                Arrays.asList("hot","dot","dog","lot","log")));

        System.out.println(solution.findLadders("red", "tax",
                Arrays.asList("ted","tex","red","tax","tad", "den", "rex", "pee")));
    }

}
