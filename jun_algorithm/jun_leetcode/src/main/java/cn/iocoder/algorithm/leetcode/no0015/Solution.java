package cn.iocoder.algorithm.leetcode.no0015;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/3sum/
 *
 * 基于多次一遍哈希实现，相当于 {@link cn.iocoder.algorithm.leetcode.no0001.Solution02} 的加强版。
 *
 * 本体更优解决，是 {@link Solution02} ，虽然本体是 O(N * N) 的时间复杂度，但是因为是理论计算，实际慢于 {@link Solution02} 。
 *
 * LeetCode 结果 266ms
 */
public class Solution {

    public List<List<Integer>> threeSum(int[] nums) {
        // 排序
        Arrays.sort(nums);

        // 获得结果
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 排除，已经组合过一次的组合。相当于减枝，避免重复计算
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int target = -nums[i];
            Map<Integer, Integer> numMap = new HashMap<>(nums.length, 1.0F);
            for (int j = i + 1; j < nums.length; j++) {
                // 排除，已经组合过一次的组合。相当于减枝，避免重复计算
                if (j + 1 < nums.length && nums[j] == nums[j + 1]) { // 要延迟去放，不然 {0, 0, 0} 的组合，就会被跳过了
                    numMap.put(nums[j], j);
                    continue;
                }
                // 避免相同数字相加，恰好相等
                Integer k = numMap.get(target - nums[j]);
                if (k != null) {
                    results.add(Arrays.asList(nums[i], nums[j], nums[k]));
                } else {
                    numMap.put(nums[j], j);
                }
            }
        }

        return results;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.threeSum(new int[]{1, -1, -1, 0}));
//        System.out.println(solution.threeSum(new int[]{0, 0, 0}));
//        System.out.println(solution.threeSum(new int[]{-1, 0, 1, 0}));
        System.out.println(solution.threeSum(new int[]{-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0}));
    }

}
