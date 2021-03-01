package cn.iocoder.algorithm.leetcode.no0001;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 一遍哈希
 */
public class Solution02 {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numIndexMap = new HashMap<Integer, Integer>(nums.length, 1.0F);
        for (int i = 0; i < nums.length; i++) {
            // 判断是否有匹配的
            Integer j = numIndexMap.get(target - nums[i]);
            if (j != null) {
                return new int[]{i, j};
            }

            // 添加到 numIndexMap 中
            numIndexMap.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoSum(new int[]{2, 7, 11, 15}, 9)));
    }

}
