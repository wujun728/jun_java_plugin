package cn.iocoder.algorithm.leetcode.no0128;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-consecutive-sequence/
 */
public class Solution {

    public int longestConsecutive(int[] nums) {
        // 创建成集合
        Set<Integer> set = new HashSet<>();
        Arrays.stream(nums).forEach(set::add);

        int max = 0;
        for (int num : nums) {
            int count = 1;
            while (set.contains(num + 1)) {
                num++;
                count++;
            }
            max = Math.max(max, count);
        }

        return max;
    }

}
