package cn.iocoder.algorithm.leetcode.no0697;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/degree-of-an-array/
 */
public class Solution {

    public int findShortestSubArray(int[] nums) {
        // 计数统计。
        // KEY：数字
        // VALUE：数量、第一个位置、最后一个位置
        Map<Integer, Integer[]> counts = new HashMap<Integer, Integer[]>();

        // 统计
        for (int i = 0; i < nums.length; i++) {
            Integer[] values = counts.get(nums[i]);
            if (values != null) { // 已存在
                values[0] = values[0] + 1; // 计数 + 1
                values[2] = i; // 变更最后位置
            } else {
                // 添加到统计
                values = new Integer[]{1, i, i};
                counts.put(nums[i], values);
            }
        }

        // 计算度
        int maxFrequency = 0; // 数字出现最大频率
        int minDegree = nums.length; // 度
        for (Map.Entry<Integer, Integer[]> entry : counts.entrySet()) {
            Integer[] values = entry.getValue();
            // 根据出现次数，做出相应逻辑
            if (values[0] < maxFrequency) { // 小于当前最大频率，则跳过
                continue;
            } else if (values[0] > maxFrequency) { // 大于当前最大频率，则直接设置
                maxFrequency = values[0];
                minDegree = values[2] - values[1] + 1;
            } else {
                minDegree = Math.min(minDegree,values[2] - values[1] + 1);
            }
        }

        return minDegree;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findShortestSubArray(new int[]{1, 2, 2, 3, 1}));
        System.out.println(solution.findShortestSubArray(new int[]{1, 2, 2, 3, 1, 4, 2}));
    }

}
