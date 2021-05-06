package cn.iocoder.algorithm.leetcode.no0229;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/majority-element-ii/submissions/
 *
 * 哈希表
 */
public class Solution {

    public List<Integer> majorityElement(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.compute(num, (key, value) -> value == null ? 1 : value + 1);
        }
        List<Integer> result = new ArrayList<>();
        int minCounts = nums.length / 3;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > minCounts) {
                result.add(entry.getKey());
            }
            if (result.size() == 2) {
                break;
            }
        }
        return result;
    }

}
