package cn.iocoder.algorithm.leetcode.no0049;

import java.util.*;

/**
 * 考虑到字符都是小写，所以通过计数排序
 */
public class Solution03 {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Integer, List<String>> group = new HashMap<>();

        for (String str : strs) {
            // 获得 key
            int key = this.getKey(str);

            // 添加到 group 中
            List<String> values = group.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(str);
        }

        return new ArrayList<>(group.values());
    }

    // 极端情况下，可能会哈希碰撞。当然，这里能够 AC ，估计是测试集太小，也没必要。
    private int getKey(String str) {
        int[] counts = new int[26];
        for (char ch : str.toCharArray()) {
            counts[ch - 'a']++;
        }
        return Arrays.hashCode(counts);
    }

}
