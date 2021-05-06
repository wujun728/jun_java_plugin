package cn.iocoder.algorithm.leetcode.no0219;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/contains-duplicate-ii/
 */
public class Solution {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            Integer oldIndex = map.put(num, i);
            if (oldIndex != null && i - oldIndex <= k) {
                return true;
            }
        }
        return false;
    }

}
