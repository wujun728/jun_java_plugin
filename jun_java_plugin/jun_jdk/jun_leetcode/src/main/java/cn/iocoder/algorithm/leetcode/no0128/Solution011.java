package cn.iocoder.algorithm.leetcode.no0128;

import java.util.HashSet;
import java.util.Set;

/**
 * 在 {@link Solution} 的基础上，进一步优化，减少重复计算
 */
public class Solution011 {

    public int longestConsecutive(int[] nums) {
        // 创建成集合
        Set<Integer> set = new HashSet<>(nums.length, 1);
        for (int num : nums) {
            set.add(num);
        }

        int max = 0;
        for (int num : nums) {
            // 如果有比它小的，这里就不计算，交给后面的
            if (set.contains(num - 1)) {
                continue;
            }
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
