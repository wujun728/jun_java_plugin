package cn.iocoder.algorithm.leetcode.no0127;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/word-ladder/
 *
 * BFS
 */
public class Solution {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        int count = 0;
        while (!queue.isEmpty()) {
            count++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                if (currentWord.equals(endWord)) {
                    return count;
                }
                for (Iterator<String> it = wordList.iterator(); it.hasNext(); ) {
                    String nextWord = it.next();
                    if (!this.canConvert(currentWord, nextWord)) {
                        continue;
                    }
                    it.remove();
                    queue.add(nextWord);
                }
            }
        }
        return 0;
    }

    public boolean canConvert(String from, String to) {
        int count = 0;
        for (int i = 0; i < from.length(); i++) {
            if (from.charAt(i) != to.charAt(i)) {
                count++;
                if (count >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.ladderLength("hit", "cog",
//                new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))));
        System.out.println(solution.ladderLength("hit", "cog",
                new ArrayList<>(Arrays.asList("hot", "dot", "dog", "lot", "log"))));
    }

}
