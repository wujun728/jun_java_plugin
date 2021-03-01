package cn.iocoder.algorithm.leetcode.no0049;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/group-anagrams/
 *
 * 使用排序实现
 */
public class Solution {

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
        char[] keyCharArray = str.toCharArray();
        Arrays.sort(keyCharArray);
        return new String(keyCharArray);
    }

}
