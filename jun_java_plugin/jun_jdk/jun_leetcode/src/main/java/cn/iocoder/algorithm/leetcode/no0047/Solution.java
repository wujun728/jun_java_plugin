package cn.iocoder.algorithm.leetcode.no0047;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutations-ii/
 *
 * 回溯算法
 */
public class Solution {

    private List<List<Integer>> result = new ArrayList<>();
    private boolean[] visits;

    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums.length == 0) {
            return Collections.emptyList();
        }
        // 排序，因为后续我们要减枝
        Arrays.sort(nums);

        // 回溯
        this.visits = new boolean[nums.length];
        this.backtracking(nums, 0, new ArrayList<>());
        return result;
    }

    private void backtracking(int[] nums, int count, List<Integer> current) {
        if (count == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        // 遍历
        Integer last = null;
        for (int i = 0; i < nums.length; i++) {
            // 已经访问，跳过
            if (visits[i]) {
                continue;
            }
            // 设置最后一个访问的数字。因为上述我们排序过，所以就访问过的，就不需要在访问了。
            if (last != null && last == nums[i]) {
                continue;
            }
            last = nums[i];
            // 添加到结果，并标记已访问
            current.add(nums[i]);
            visits[i] = true;
            // 递归
            this.backtracking(nums, count + 1, current);
            // 移除出结果，并标记未访问
            current.remove(current.size() - 1);
            visits[i] = false;
        }
    }

}
