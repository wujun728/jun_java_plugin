package cn.iocoder.algorithm.leetcode.no0046;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutations/
 *
 * 回溯算法
 */
public class Solution {

    private boolean[] visits;
    private List<List<Integer>> results;

    public List<List<Integer>> permute(int[] nums) {
        this.visits = new boolean[nums.length];
        this.results = new ArrayList<>();

        // 回溯
        this.backtracking(nums, 0, new ArrayList<>());

        return results;
    }

    private void backtracking(int[] nums, int visitSize, List<Integer> current) {
        // 访问到达上限
        if (visitSize == nums.length) {
            results.add(new ArrayList<>(current));
            return;
        }
        // 遍历
        for (int i = 0; i < nums.length; i++) {
            if (visits[i]) {
                continue;
            }
            // 标记已使用，并添加到结果
            visits[i] = true;
            current.add(nums[i]);

            // 回溯
            this.backtracking(nums, visitSize + 1, current);

            // 调剂为使用，并从结束移除
            current.remove(current.size() - 1);
            visits[i] = false;
        }
    }

}
