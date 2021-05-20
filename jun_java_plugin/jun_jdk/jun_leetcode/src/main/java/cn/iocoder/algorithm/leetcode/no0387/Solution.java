package cn.iocoder.algorithm.leetcode.no0387;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/first-unique-character-in-a-string/
 *
 * 哈希表
 */
public class Solution {

    public int firstUniqChar(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        Map<Character, Integer> indexes = new LinkedHashMap<>();
        // 计数 + 位置
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            counts.put(ch, counts.getOrDefault(ch, 0) + 1);
            indexes.putIfAbsent(ch, i);
        }
        // 遍历获得结果
        for (Map.Entry<Character, Integer> index : indexes.entrySet()) {
            if (counts.get(index.getKey()) == 1) {
                return index.getValue();
            }
        }
        return -1;
    }

}
