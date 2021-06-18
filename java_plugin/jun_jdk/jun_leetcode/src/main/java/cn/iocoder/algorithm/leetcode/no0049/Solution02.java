package cn.iocoder.algorithm.leetcode.no0049;

import java.util.*;

/**
 * 考虑到字符都是小写，所以通过计数排序
 */
public class Solution02 {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> group = new HashMap<>();

        for (String str : strs) {
            // 获得 key
            String key = this.getKey(str);

            // 添加到 group 中
            List<String> values = group.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(str);
        }

        return new ArrayList<>(group.values());
    }

    private String getKey(String str) {
        int[] counts = new int[26];
        for (char ch : str.toCharArray()) {
            counts[ch - 'a']++;
        }
        StringBuilder sb = new StringBuilder(26 * 2);
        for (int count : counts) {
            sb.append(count).append("#");
        }
        return sb.toString();
    }

}
