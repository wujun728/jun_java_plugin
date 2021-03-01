package cn.iocoder.algorithm.leetcode.no0078;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/subsets/
 *
 * 回溯算法
 */
public class Solution {

    private List<List<Integer>> results = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        this.backtracking(nums, 0, new ArrayList<>());
        return results;
    }

    private void backtracking(int[] nums, int index, List<Integer> current) {
        // 添加到结果
        results.add(new ArrayList<>(current));

        // 回溯
        for (int i = index; i < nums.length; i++) {
            current.add(nums[i]);
            this.backtracking(nums, i + 1, current);
            current.remove(current.size() - 1);
        }
    }

}
