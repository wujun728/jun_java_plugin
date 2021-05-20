package cn.iocoder.algorithm.leetcode.no0090;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/subsets-ii/
 *
 * 回溯算法
 */
public class Solution {

    private List<List<Integer>> results = new ArrayList<>();

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        this.backtracking(nums, 0, new ArrayList<>());
        return results;
    }

    private void backtracking(int[] nums, int index, List<Integer> current) {
        // 添加到结果
        results.add(new ArrayList<>(current));

        // 回溯
        Integer last = null;
        for (int i = index; i < nums.length; i++) {
            if (last != null && last == nums[i]) {
                continue;
            }
            last = nums[i];
            // 继续回溯
            current.add(nums[i]);
            this.backtracking(nums, i + 1, current);
            current.remove(current.size() - 1);
        }
    }

}
