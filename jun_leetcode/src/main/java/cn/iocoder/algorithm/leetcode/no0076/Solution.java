package cn.iocoder.algorithm.leetcode.no0076;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/minimum-window-substring/
 *
 * 顺序滑动窗口
 *
 * 勉强通过，184ms ，超过 5% 的提交。
 *
 * 实际上，进一步优化，可以只计数，不需要记录位置。
 */
public class Solution {

    private static final int INDEX_NULL = -1;

    private static class Node {

        /**
         * 需要满足的数量
         */
        private Integer count;
        /**
         * 使用的位置
         */
        private List<Integer> indexes;

        public Node(Integer count, List<Integer> indexes) {
            this.count = count;
            this.indexes = indexes;
        }

        public boolean isFull() {
            return indexes.size() == count;
        }

        public void add(Integer index) {
            // 如果已经满了，移除
            if (this.isFull()) {
//                indexes.iterator().remove();
                indexes.remove(0);
            }
            // 添加到结果
            indexes.add(index);
        }

        public boolean exists(Integer index) {
            return indexes.contains(index);
        }

    }

    public String minWindow(String s, String t) {
        // 索引
        Map<Character, Node> indexesMap = new HashMap<>();
        int needIndexCount = t.length();
//        int min = s.length() + 1; // 不满足
        String result = "";
        int beginIndex = INDEX_NULL;
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            if (!indexesMap.containsKey(ch)) {
                indexesMap.put(ch, new Node(1, new ArrayList<>()));
            } else {
                indexesMap.get(ch).count++;
            }
        }

        // 遍历字符串
        for (int i = 0; i < s.length(); i++) {
            // 判断字符是否需要匹配
            char ch = s.charAt(i);
            Node node = indexesMap.get(ch);
            if (node == null) {
                continue;
            }
            // 判断是否全部匹配完了
            if (!node.isFull()) {
                needIndexCount--;
            }
            // 记录位置
            node.add(i);
            // 如果未索引，并且是第一次索引到，记录下位置
            if (beginIndex == INDEX_NULL) {
                beginIndex = i;
            }

            // 移除开始多余的字符
            for (int j = Math.max(beginIndex, 0); j < i; j++) {
                char jCh = s.charAt(j);
                Node jNode = indexesMap.get(jCh);
                // 非需要查找的字符，移除
                if (jNode == null) {
                    beginIndex = j;
                    continue;
                }
                // 虽然是要查找的字符，但是被后面的字符所取代的，移除
                if (!jNode.exists(j)) {
                    beginIndex = j;
                    continue;
                }
                beginIndex = j;
                break;
            }

            // 计算是否满足
            if (needIndexCount == 0) {
                int length = i - beginIndex + 1;
                if (result.length() == 0 || length < result.length()) {
                    result = s.substring(beginIndex, i + 1);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.minWindow("ADOBECODEBANC", "ABC"));
//        System.out.println(solution.minWindow("ADOBECODEBANC", "Z"));
//        System.out.println(solution.minWindow("a", "aa"));
    }

}
