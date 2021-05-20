package cn.iocoder.algorithm.leetcode.no0594;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/longest-harmonious-subsequence/
 */
public class Solution {

    public int findLHS(int[] nums) {
        // 计数
        Map<Integer, Integer> counts = new HashMap<>();
        Arrays.stream(nums).forEach(num -> counts.put(num, counts.getOrDefault(num, 0) + 1));

        // 计算
        int max = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (!counts.containsKey(entry.getKey() + 1)) {
                continue;
            }
            max = Math.max(max, entry.getValue() + counts.getOrDefault(entry.getKey() + 1, 0));
        }
        return max;
    }

}
